plugins {
    id("net.minecraftforge.gradle")
    id("com.modrinth.minotaur")
    id("minecraft-mutex")
}

fun prop(key: String): String = sc.properties.get<String>(key)

val minecraftVersion = sc.current.version
val javaVersion = if (sc.current.parsed >= "1.20.5") 21 else 17
val mixinCompatibility = if (sc.current.parsed >= "1.21") javaVersion else 17
val mixinConfig = "biomes-were-already-plenty.client.mixins.json"

version = "${prop("mod.version")}+$minecraftVersion"
group = prop("mod.group")
base.archivesName = "${prop("mod.id")}-forge"

sourceSets.main {
    java.exclude("**/fabric/**", "**/neoforge/**")
    resources.srcDir(rootProject.file("src/forge/resources"))
}

sourceSets.configureEach {
    val output = layout.buildDirectory.dir("sourceSets/$name")
    this.output.setResourcesDir(output)
    java.destinationDirectory = output
}

minecraft {
    mappings("official", minecraftVersion)

    runs {
        register("client") {
            workingDir.set(file("run"))
            args("--mixin.config", mixinConfig)
        }
    }
}

repositories {
    minecraft.mavenizer(this)
    maven(fg.forgeMaven)
    maven(fg.minecraftLibsMaven)
    mavenCentral()
    maven("https://maven.shedaniel.me/")
    exclusiveContent {
        forRepository { maven("https://maven.minecraftforge.net/") }
        filter { includeGroup("com.github.glitchfiend") }
    }
}

configurations.runtimeClasspath {
    resolutionStrategy.force("net.sf.jopt-simple:jopt-simple:5.0.4")
}

dependencies {
    implementation(minecraft.dependency("net.minecraftforge:forge:$minecraftVersion-${prop("deps.forge")}"))
    implementation("me.shedaniel.cloth:cloth-config-forge:${prop("deps.cloth_config")}") { isTransitive = false }

    compileOnly("com.github.glitchfiend:BiomesOPlenty-forge:${prop("deps.biomesoplenty")}") { isTransitive = false }
    runtimeOnly("com.github.glitchfiend:BiomesOPlenty-forge:${prop("deps.biomesoplenty")}") { isTransitive = false }
    runtimeOnly("com.github.glitchfiend:GlitchCore-forge:${prop("deps.glitchcore")}") { isTransitive = false }
    runtimeOnly("com.github.glitchfiend:TerraBlender-forge:${prop("deps.terrablender")}") { isTransitive = false }
}

java {
    withSourcesJar()
    toolchain.languageVersion = JavaLanguageVersion.of(javaVersion)
}

val expansions = mapOf(
    "version" to version.toString(),
    "minecraft" to prop("mod.mc_compat"),
    "java" to mixinCompatibility.toString(),
    "pack_format" to prop("mod.pack_format"),
)

tasks.processResources {
    inputs.properties(expansions)
    filesMatching(listOf("META-INF/mods.toml", "pack.mcmeta", "*.mixins.json")) { expand(expansions) }
}

tasks.jar {
    val archivesName = base.archivesName
    inputs.property("archivesName", archivesName)
    from(rootProject.file("LICENSE")) {
        rename { "${it}_${archivesName.get()}" }
    }
    manifest.attributes("MixinConfigs" to mixinConfig)
}

modrinth {
    token = providers.environmentVariable("MODRINTH_TOKEN")
    projectId = "d6x6KYTn"
    versionNumber = "$version-forge"
    versionName = "$version-forge"
    versionType = providers.environmentVariable("VERSION_TYPE").orElse("release")
    changelog = providers.environmentVariable("CHANGELOG_FILE").map { rootProject.file(it).readText() }.orElse("")
    file = tasks.jar.flatMap { it.archiveFile }
    gameVersions.addAll(sc.properties.rawOrNull("mod", "mc_releases")?.asList().orEmpty().map { it.toString() })
    loaders.add("forge")
    dependencies {
        required.project("cloth-config")
        optional.project("biomes-o-plenty")
    }
}

tasks.named("modrinthSyncBody") {
    enabled = false
}
