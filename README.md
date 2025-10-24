# Dragon Egg Container Protection

A Minecraft Fabric mod written in Kotlin that prevents the Ender Dragon Egg from being placed into any containers.

## Features

- **Blocks manual placement**: Prevents players from clicking dragon eggs into container slots (chests, barrels, hoppers, etc.)
- **Blocks shift-clicking**: Prevents players from shift-clicking dragon eggs from their inventory into containers
- **Blocks hoppers**: Prevents hoppers from transferring dragon eggs between containers
- **Blocks droppers**: Prevents droppers from dispensing dragon eggs into containers
- **Blocks hopper minecarts**: Prevents hopper minecarts from transferring dragon eggs
- **Works with all containers**: Applies to all container types including chests, barrels, shulker boxes, hoppers, and modded containers
- **Player inventory allowed**: Dragon eggs can still be moved within the player's own inventory and hotbar

## How it works

The mod uses multiple Mixins to intercept various item transfer methods:
- **ScreenHandlerMixin**: Intercepts manual clicking and shift-clicking in container GUIs
- **HopperBlockEntityMixin**: Intercepts hopper item transfers
- **DropperBlockMixin**: Intercepts dropper dispensing actions
- **HopperMinecartEntityMixin**: Intercepts hopper minecart operations

When any of these methods attempt to move a dragon egg, the action is cancelled.

## Technical Details

- **Platform**: Fabric
- **Language**: Kotlin
- **Minecraft Version**: Check `gradle.properties` for the target version
- **Implementation**: Uses Mixin injection to modify vanilla behavior

## Installation

1. Ensure you have Fabric Loader and Fabric API installed
2. Ensure you have Fabric Language Kotlin installed
3. Place the mod JAR file in your `.minecraft/mods` folder
4. Launch Minecraft

## Building from Source

```bash
./gradlew build
```

The compiled JAR will be in `build/libs/`

## License

See LICENSE.txt
