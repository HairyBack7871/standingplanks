<p align="center">
  <img src="src/main/resources/assets/standings_planks/icon.png" width="160" alt="Standing Planks Icon">
</p>



# Standing Planks
A mod for Minecraft that gives you vertical planks for every wood type in the game. Standing Planks lets you place any plank block upright, adding a clean vertical‑board look that fits naturally into vanilla building styles. Every plank variant uses the exact same textures, sounds, and behavior you already know—just rotated into a vertical orientation so you can build walls, trims, floors, and decorative details that weren’t possible before.

Standing Planks works seamlessly in survival: simply hit planks with a stick to toggle between horizontal and vertical orientation. Because the mod reuses the existing plank blocks with a lightweight blockstate property, it stays fully compatible with resource packs, modpacks, and other mods that add new plank types.

## Features
- Vertical variants for all plank types
- Natural integration with vanilla textures and resource packs
- Survival‑friendly placement: sneak‑place to toggle orientation
- Fully compatible with other mods and modded wood sets

## Supported Versions
Minecraft 1.21.4 (Fabric)

## Technical Details
Standing Planks is built for the Fabric modding ecosystem, using the Fabric Loader and Fabric API. The mod extends the existing plank blocks with a new vertical blockstate property. When the property is set to true, the block uses a rotated model that displays the plank texture vertically. This approach avoids creating new block IDs and ensures full compatibility with:
- resource packs
- modded wood types
- block tags
- loot tables
- recipes

The project uses Gradle and follows the standard Fabric project structure, making it easy to explore or extend.

For development, IntelliJ IDEA works perfectly.
Open the project folder, let IntelliJ import the Gradle build, and you can run, debug, or modify the mod directly from the IDE.

## License
This project is licensed under the MIT License.
See the LICENSE file for details.
