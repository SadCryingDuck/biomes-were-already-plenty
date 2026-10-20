# Biomes Were Already Plenty

Client-side mod that stops the Anomaly block from Biomes O' Plenty flickering and tanking your FPS.

| Loader   | Minecraft versions                                      |
|----------|---------------------------------------------------------|
| Fabric   | 1.20.1, 1.20.4, 1.20.6, 1.21 to 1.21.11, 26.1.2 to 26.3 |
| NeoForge | 1.20.4, 1.20.6, 1.21 to 1.21.11, 26.1.2 to 26.3         |
| Forge    | 1.20.1, 1.20.6, 1.21 to 1.21.4                          |

> No 1.21.2, 26.1 or 26.1.1 versions exist since Biomes O' Plenty has no release for them. Forge stops at 1.21.4 since cloth config has no forge release after it :sob:

## Before / After

| Before | After |
|--------|-------|
| ![Before - Anomaly block rapidly cycling through random block textures](screenshots/before.webp) | ![After - Anomaly block showing its own static texture](screenshots/after.webp) |

## What it does

The Anomaly block rapidly cycles through random block textures when you get close to it, which tanks your FPS. This mod makes it show its own texture instead.

## Config

Via Mod Menu on Fabric, the mods menu screen on NeoForge and Forge, or `.minecraft/config/biomes-were-already-plenty.json` to change the settings through a textfile edit

| Option                       | Default | Description                                            |
|------------------------------|---------|--------------------------------------------------------|
| Fix Anomaly Block Flickering | true    | Stops the block cycling through random block states    |
| Freeze Anomaly Texture       | false   | Freezes the texture on the first frame (requires F3+T) |

## Dependencies

- [Cloth Config](https://modrinth.com/mod/cloth-config)
- [Fabric API](https://modrinth.com/mod/fabric-api) (Fabric only)
- [Mod Menu](https://modrinth.com/mod/modmenu) (Fabric only, optional)

## Building

Builds without any extra steps. BOP and Mod Menu are pulled in automatically for IDE support and for testing with `./gradlew :1.21.1-fabric:runClient` (or any other `<version>-<loader>`), so no more evil red texts.

## License

[GPL-3.0](LICENSE)
