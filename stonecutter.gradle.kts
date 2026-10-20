plugins {
    id("dev.kikugie.stonecutter")
}

stonecutter active "1.21.1-fabric"

stonecutter parameters {
    val (version, loader) = current.project.split('-', limit = 2)

    properties {
        tags(version, loader)
    }

    replacements {
        string(current.parsed >= "1.21.11") {
            replace("ResourceLocation", "Identifier")
        }
    }
}

stonecutter tasks {
    order("modrinth")
}
