plugins {
    id("dev.kikugie.loom-back-compat")
    id("org.jetbrains.kotlin.jvm")
    id("com.modrinth.minotaur")
}

val minecraftVersion = sc.current.version
val javaVersion = when {
    sc.current.parsed >= "26.1" -> 25
    sc.current.parsed >= "1.20.5" -> 21
    else -> 17
}

version = "${property("mod.version")}+$minecraftVersion"
group = property("mod.group").toString()
base.archivesName = property("mod.id").toString()

repositories {
    maven("https://maven.shedaniel.me/")
    maven("https://maven.terraformersmc.com/releases/")
    exclusiveContent {
        forRepository { maven("https://api.modrinth.com/maven") }
        filter { includeGroup("maven.modrinth") }
    }
}

loom {
    splitEnvironmentSourceSets()

    mods {
        register("biomes-were-already-plenty") {
            sourceSet(sourceSets["main"])
            sourceSet(sourceSets["client"])
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    loomx.applyMojangMappings()

    modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${property("deps.fabric_kotlin")}")
    modImplementation("me.shedaniel.cloth:cloth-config-fabric:${property("deps.cloth_config")}") {
        exclude(group = "net.fabricmc.fabric-api")
    }

    modCompileOnly("com.terraformersmc:modmenu:${property("deps.modmenu")}")
    modLocalRuntime("com.terraformersmc:modmenu:${property("deps.modmenu")}")

    modCompileOnly("maven.modrinth:biomes-o-plenty:${property("deps.biomesoplenty")}")
    modLocalRuntime("maven.modrinth:biomes-o-plenty:${property("deps.biomesoplenty")}")
    modLocalRuntime("maven.modrinth:glitchcore:${property("deps.glitchcore")}")
    modLocalRuntime("maven.modrinth:terrablender:${property("deps.terrablender")}")
    localRuntime("com.electronwill.night-config:core:${property("deps.nightconfig")}")
    localRuntime("com.electronwill.night-config:toml:${property("deps.nightconfig")}")
    localRuntime("net.jodah:typetools:${property("deps.typetools")}")
}

java {
    withSourcesJar()
    toolchain.languageVersion = JavaLanguageVersion.of(javaVersion)
}

val expansions = mapOf(
    "version" to version.toString(),
    "minecraft" to property("mod.mc_compat").toString(),
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
    versionNumber = version.toString()
    versionType = providers.environmentVariable("VERSION_TYPE").orElse("release")
    changelog = providers.environmentVariable("CHANGELOG_FILE").map { rootProject.file(it).readText() }.orElse("")
    file = loomx.modJar.flatMap { it.archiveFile }
    gameVersions.addAll(sc.properties.rawOrNull("mod", "mc_releases")?.asList().orEmpty().map { it.toString() })
    loaders.add("fabric")
    syncBodyFrom = providers.fileContents(rootProject.layout.projectDirectory.file("MODRINTH.md")).asText
    dependencies {
        required.project("fabric-api")
        required.project("fabric-language-kotlin")
        required.project("cloth-config")
        optional.project("modmenu")
        optional.project("biomes-o-plenty")
    }
}

tasks.named("modrinthSyncBody") {
    onlyIf { sc.current.isActive }
}
