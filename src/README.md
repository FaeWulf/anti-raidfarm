# Anti-RaidFarm Mod

**Anti-RaidFarm** mod for Fabric Minecraft is designed to prevent players from exploiting infinite bad omen loops in
raid farms. This mod introduces a configurable delay timer to manage and restrict the initiation of raids, ensuring a
fair and balanced gameplay experience.

## Installation

1. **Download the Mod**: Ensure you have the Fabric mod loader installed for Minecraft version 1.20.6.
2. **Install the Mod**: Place the `Anti-RaidFarm.jar` file into the `mods` folder in your Minecraft directory.
3. **Run the Game**: Launch Minecraft using the Fabric profile to activate the mod.

## Configuration

The delay timer can be configured in the mod's configuration file. By default, the configuration file is located in
the `config` folder of your Minecraft directory. Edit the file to adjust the delay between raids:

```json
{
  "wait_time": 180
}
```

## Permission

```
antiraidfarm.bypass: allow to bypass delay timer
```
