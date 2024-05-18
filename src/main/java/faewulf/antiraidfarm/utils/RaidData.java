package faewulf.antiraidfarm.utils;

import net.minecraft.nbt.NbtCompound;

public class RaidData {
    public static void setData(IPlayerDataSaver player, String Data) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putString("lastRaid", Data);
    }

    public static void resetData(IPlayerDataSaver player, String Data) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putString("lastRaid", "");
    }

    public static String getData(IPlayerDataSaver player) {
        return player.getPersistentData().getString("lastRaid");
    }
}
