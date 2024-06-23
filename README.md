# Anti-RaidFarm Mod

## 📖Overview

**Anti-RaidFarm** mod is designed to prevent players from exploiting infinite bad omen loops in
raid farms. This mod introduces a configurable delay timer to manage and restrict the initiation of raids, ensuring a
fair and balanced gameplay experience.

After player started a raid, a timer will be set for that player, prevent from trigger other raid during the cooldown
timer.
You can check the timer by holding an Ominous Bottle.

<p align="center">
 <img src="https://github.com/FaeWulf/public-imgs/blob/main/mods/anti-raidfarm/ss.png?raw=true" alt="example">
</p>

## ⚙️Configuration

The delay timer can be configured in the mod's configuration file. By default, the configuration file is located in
the `config` folder of your Minecraft directory.

Edit the file to adjust the delay (in second) between raids:

```json
{
  "wait_time": 180
}
```

## 🪧Permission

```
antiraidfarm.bypass: allow to bypass delay timer
```

Note: if no permission manager mod installed, then this bypass permission will bypass if player's permission level equal
or greater than 3.