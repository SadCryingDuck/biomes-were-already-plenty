pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/") { name = "Fabric" }
        maven("https://maven.neoforged.net/releases/") { name = "NeoForged" }
        maven("https://maven.minecraftforge.net/") { name = "MinecraftForge" }
        maven("https://maven.kikugie.dev/releases") { name = "KikuGie Releases" }
    }

    plugins {
        id("com.modrinth.minotaur") version "2.+"
        id("net.neoforged.moddev") version "2.0.148"
        id("net.neoforged.moddev.legacyforge") version "2.0.148"
        id("net.minecraftforge.gradle") version "7.0.40"
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9.8"
    id("dev.kikugie.loom-back-compat") version "0.4.2"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

stonecutter {
    create(rootProject) {
        fun match(minecraft: String, vararg loaders: String) {
            for (loader in loaders) version("$minecraft-$loader", minecraft).buildscript("build.$loader.gradle.kts")
        }

        match("1.20.1", "fabric")
        version("1.20.1-forge", "1.20.1").buildscript("build.forge-legacy.gradle.kts")
        match("1.20.4", "fabric", "neoforge")
        match("1.20.6", "fabric", "neoforge", "forge")
        match("1.21", "fabric", "neoforge", "forge")
        match("1.21.1", "fabric", "neoforge", "forge")
        match("1.21.3", "fabric", "neoforge", "forge")
        match("1.21.4", "fabric", "neoforge", "forge")
        match("1.21.5", "fabric", "neoforge")
        match("1.21.6", "fabric", "neoforge")
        match("1.21.7", "fabric", "neoforge")
        match("1.21.8", "fabric", "neoforge")
        match("1.21.9", "fabric", "neoforge")
        match("1.21.10", "fabric", "neoforge")
        match("1.21.11", "fabric", "neoforge")
        match("26.1.2", "fabric", "neoforge")
        match("26.2", "fabric", "neoforge")
        match("26.3", "fabric", "neoforge")
        vcsVersion = "1.21.1-fabric"
    }
}

rootProject.name = "biomes-were-already-plenty"
