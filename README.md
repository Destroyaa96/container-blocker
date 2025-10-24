# Container Blocker

A lightweight Minecraft Fabric mod that prevents configured items from being placed into containers. By default, it blocks the Ender Dragon Egg, but can be configured to block any item.

## Features

- **Configurable blocked items**: Customize which items cannot enter containers via `config/blocked_items.json`
- **Blocks manual placement**: Prevents players from clicking blocked items into container slots (chests, barrels, hoppers, etc.)
- **Blocks shift-clicking**: Prevents players from shift-clicking blocked items from their inventory into containers
- **Blocks hoppers**: Prevents hoppers from transferring blocked items between containers
- **Works with all containers**: Applies to all container types including chests, barrels, shulker boxes, hoppers, and modded containers
- **Player inventory allowed**: Blocked items can still be moved within the player's own inventory and hotbar

## Configuration

The mod creates a `config/blocked_items.json` file on first run. You can edit this file to customize which items are blocked:

```json
{
  "blockedItems": [
    "minecraft:dragon_egg",
    "minecraft:nether_star"
  ]
}
```

## How it works

The mod uses Mixins to intercept item transfer methods:
- **ScreenHandlerMixin**: Intercepts manual clicking and shift-clicking in container GUIs
- **HopperBlockEntityMixin**: Intercepts hopper item transfers, extraction, and merging

When any of these methods attempt to move a blocked item into a container, the action is cancelled.

## Technical Details

- **Platform**: Fabric
- **Language**: Java
- **Minecraft Version**: 1.21.2 and higher
- **Implementation**: Uses Mixin injection to modify vanilla behavior

## Installation

1. Ensure you have Fabric Loader and Fabric API installed
2. Place the mod JAR file in your `.minecraft/mods` folder
3. Launch Minecraft
4. Edit `config/blocked_items.json` to configure blocked items (optional)

## Building from Source

```bash
./gradlew build
```

The compiled JAR will be in `build/libs/`

## License

MIT License - See LICENSE.txt
