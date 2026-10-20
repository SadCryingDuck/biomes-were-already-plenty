plugins {
    id("net.neoforged.moddev")
    id("com.modrinth.minotaur")
    id("minecraft-mutex")
}

fun prop(key: String): String = sc.properties.get<String>(key)

val minecraftVersion = sc.current.version
val javaVersion = when {
    sc.current.parsed >= "26.1" -> 25
    sc.current.parsed >= "1.20.5" -> 21
    else -> 17
}
val legacyMetadata = sc.current.parsed < "1.20.5"

version = "${prop("mod.version")}+$minecraftVersion"
group = prop("mod.group")
base.archivesName = "${prop("mod.id")}-neoforge"

repositories {
    maven("https://maven.shedaniel.me/")
    exclusiveContent {
        forRepository { maven("https://maven.minecraftforge.net/") }
        filter { includeGroup("com.github.glitchfiend") }
    }
}

sourceSets.main {
    java.exclude("**/fabric/**", "**/forge/**")
    resources.srcDir(rootProject.file("src/neoforge/resources"))
}

neoForge {
    version = prop("deps.neoforge")

    runs {
        register("client") {
            client()
            gameDirectory = file("run")
        }
    }

    mods {
        register("biomes_were_already_plenty") {
            sourceSet(sourceSets.main.get())
        }
    }
}

dependencies {
    implementation("me.shedaniel.cloth:cloth-config-neoforge:${prop("deps.cloth_config")}") { isTransitive = false }

    compileOnly("com.github.glitchfiend:BiomesOPlenty-neoforge:${prop("deps.biomesoplenty")}") { isTransitive = false }
    runtimeOnly("com.github.glitchfiend:BiomesOPlenty-neoforge:${prop("deps.biomesoplenty")}")
    runtimeOnly("com.github.glitchfiend:GlitchCore-neoforge:${prop("deps.glitchcore")}")
    runtimeOnly("com.github.glitchfiend:TerraBlender-neoforge:${prop("deps.terrablender")}")
}

java {
    withSourcesJar()
    toolchain.languageVersion = JavaLanguageVersion.of(javaVersion)
}

val expansions = mapOf(
    "version" to version.toString(),
    "minecraft" to prop("mod.mc_compat"),
    "java" to javaVersion.toString(),
    "icon_key" to if (sc.current.parsed >= "26.2") "iconFile" else "logoFile",
)

tasks.processResources {
    inputs.properties(expansions)
    filesMatching(listOf("META-INF/neoforge.mods.toml", "*.mixins.json")) { expand(expansions) }
    if (legacyMetadata) {
        filesMatching("META-INF/neoforge.mods.toml") { name = "mods.toml" }
    }
}

tasks.jar {
    val archivesName = base.archivesName
    inputs.property("archivesName", archivesName)
    from(rootProject.file("LICENSE")) {
        rename { "${it}_${archivesName.get()}" }
    }
}

modrinth {
    token = providers.environmentVariable("MODRINTH_TOKEN")
    projectId = "d6x6KYTn"
    versionNumber = "$version-neoforge"
    versionName = "$version-neoforge"
    versionType = providers.environmentVariable("VERSION_TYPE").orElse("release")
    changelog = providers.environmentVariable("CHANGELOG_FILE").map { rootProject.file(it).readText() }.orElse("")
    file = tasks.jar.flatMap { it.archiveFile }
    gameVersions.addAll(sc.properties.rawOrNull("mod", "mc_releases")?.asList().orEmpty().map { it.toString() })
    loaders.add("neoforge")
    dependencies {
        required.project("cloth-config")
        optional.project("biomes-o-plenty")
    }
}

tasks.named("modrinthSyncBody") {
    enabled = false
}
