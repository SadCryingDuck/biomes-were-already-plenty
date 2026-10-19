pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/") { name = "Fabric" }
        maven("https://maven.kikugie.dev/releases") { name = "KikuGie Releases" }
    }

    plugins {
        id("org.jetbrains.kotlin.jvm") version "2.3.20"
        id("com.modrinth.minotaur") version "2.+"
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9.8"
    id("dev.kikugie.loom-back-compat") version "0.4.2"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

stonecutter {
    create(rootProject) {
        versions(
            "1.20.1",
            "1.21", "1.21.1", "1.21.3", "1.21.4", "1.21.5",
            "1.21.6", "1.21.7", "1.21.8", "1.21.9", "1.21.10", "1.21.11",
        )
        vcsVersion = "1.21.1"
    }
}

rootProject.name = "biomes-were-already-plenty"
