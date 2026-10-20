plugins {
    id("net.neoforged.moddev.legacyforge")
    id("com.modrinth.minotaur")
    id("minecraft-mutex")
}

fun prop(key: String): String = sc.properties.get<String>(key)

val minecraftVersion = sc.current.version
val javaVersion = 17
val mixinConfig = "biomes-were-already-plenty.client.mixins.json"
val refmap = "biomes-were-already-plenty.refmap.json"

version = "${prop("mod.version")}+$minecraftVersion"
group = prop("mod.group")
base.archivesName = "${prop("mod.id")}-forge"

repositories {
    maven("https://maven.shedaniel.me/")
    exclusiveContent {
        forRepository { maven("https://maven.minecraftforge.net/") }
        filter { includeGroup("com.github.glitchfiend") }
    }
}

sourceSets.main {
    java.exclude("**/fabric/**", "**/neoforge/**")
    resources.srcDir(rootProject.file("src/forge/resources"))
}

legacyForge {
    enable {
        forgeVersion = "${sc.current.version}-${prop("deps.forge")}"
    }

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

mixin {
    add(sourceSets.main.get(), refmap)
    config(mixinConfig)
}

dependencies {
    annotationProcessor("org.spongepowered:mixin:0.8.5:processor")

    modImplementation("me.shedaniel.cloth:cloth-config-forge:${prop("deps.cloth_config")}")

    modCompileOnly("com.github.glitchfiend:BiomesOPlenty-forge:${prop("deps.biomesoplenty")}")
    modRuntimeOnly("com.github.glitchfiend:BiomesOPlenty-forge:${prop("deps.biomesoplenty")}")
    modRuntimeOnly("com.github.glitchfiend:GlitchCore-forge:${prop("deps.glitchcore")}")
    modRuntimeOnly("com.github.glitchfiend:TerraBlender-forge:${prop("deps.terrablender")}")
    runtimeOnly("com.electronwill.night-config:core:${prop("deps.nightconfig")}")
    runtimeOnly("com.electronwill.night-config:toml:${prop("deps.nightconfig")}")
    runtimeOnly("net.jodah:typetools:${prop("deps.typetools")}")
}

java {
    withSourcesJar()
    toolchain.languageVersion = JavaLanguageVersion.of(javaVersion)
}

val expansions = mapOf(
    "version" to version.toString(),
    "minecraft" to prop("mod.mc_compat"),
    "java" to javaVersion.toString(),
    "pack_format" to prop("mod.pack_format"),
)

tasks.processResources {
    inputs.properties(expansions)
    filesMatching(listOf("META-INF/mods.toml", "pack.mcmeta", "*.mixins.json")) { expand(expansions) }
    filesMatching(mixinConfig) {
        filter { line -> if (line.contains("\"package\"")) "$line\n  \"refmap\": \"$refmap\"," else line }
    }
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
    file = tasks.named<org.gradle.jvm.tasks.Jar>("reobfJar").flatMap { it.archiveFile }
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
