plugins {
    id("dev.kikugie.loom-back-compat")
    id("com.modrinth.minotaur")
}

fun prop(key: String): String = sc.properties.get<String>(key)

val minecraftVersion = sc.current.version
val javaVersion = when {
    sc.current.parsed >= "26.1" -> 25
    sc.current.parsed >= "1.20.5" -> 21
    else -> 17
}

version = "${prop("mod.version")}+$minecraftVersion"
group = prop("mod.group")
base.archivesName = "${prop("mod.id")}-fabric"

repositories {
    maven("https://maven.shedaniel.me/")
    maven("https://maven.terraformersmc.com/releases/")
    exclusiveContent {
        forRepository { maven("https://maven.minecraftforge.net/") }
        filter { includeGroup("com.github.glitchfiend") }
    }
}

sourceSets.main {
    java.exclude("**/neoforge/**", "**/forge/**")
    resources.srcDir(rootProject.file("src/fabric/resources"))
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    loomx.applyMojangMappings()

    modImplementation("net.fabricmc:fabric-loader:${prop("deps.fabric_loader")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${prop("deps.fabric_api")}")
    modImplementation("me.shedaniel.cloth:cloth-config-fabric:${prop("deps.cloth_config")}") {
        exclude(group = "net.fabricmc.fabric-api")
    }

    modCompileOnly("com.terraformersmc:modmenu:${prop("deps.modmenu")}") { isTransitive = false }
    modLocalRuntime("com.terraformersmc:modmenu:${prop("deps.modmenu")}") { isTransitive = false }

    modCompileOnly("com.github.glitchfiend:BiomesOPlenty-fabric:${prop("deps.biomesoplenty")}")
    modLocalRuntime("com.github.glitchfiend:BiomesOPlenty-fabric:${prop("deps.biomesoplenty")}")
    modLocalRuntime("com.github.glitchfiend:GlitchCore-fabric:${prop("deps.glitchcore")}")
    modLocalRuntime("com.github.glitchfiend:TerraBlender-fabric:${prop("deps.terrablender")}")
}

java {
    withSourcesJar()
    toolchain.languageVersion = JavaLanguageVersion.of(javaVersion)
}

val expansions = mapOf(
    "version" to version.toString(),
    "minecraft" to prop("mod.mc_compat"),
    "java" to javaVersion.toString(),
)

tasks.withType<ProcessResources>().configureEach {
    inputs.properties(expansions)
    filesMatching(listOf("fabric.mod.json", "*.mixins.json")) { expand(expansions) }
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
    versionNumber = "$version-fabric"
    versionName = "$version-fabric"
    versionType = providers.environmentVariable("VERSION_TYPE").orElse("release")
    changelog = providers.environmentVariable("CHANGELOG_FILE").map { rootProject.file(it).readText() }.orElse("")
    file = loomx.modJar.flatMap { it.archiveFile }
    gameVersions.addAll(sc.properties.rawOrNull("mod", "mc_releases")?.asList().orEmpty().map { it.toString() })
    loaders.add("fabric")
    syncBodyFrom = providers.fileContents(rootProject.layout.projectDirectory.file("MODRINTH.md")).asText
    dependencies {
        required.project("fabric-api")
        required.project("cloth-config")
        optional.project("modmenu")
        optional.project("biomes-o-plenty")
    }
}

tasks.named("modrinthSyncBody") {
    onlyIf { sc.current.isActive }
}
