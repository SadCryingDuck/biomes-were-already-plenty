import org.gradle.api.services.BuildService
import org.gradle.api.services.BuildServiceParameters

interface MinecraftMutex : BuildService<BuildServiceParameters.None>

val mutex = gradle.sharedServices.registerIfAbsent("minecraftMutex", MinecraftMutex::class.java) {
    maxParallelUsages.set(1)
}

tasks.named { it == "createMinecraftArtifacts" }.configureEach {
    usesService(mutex)
}
