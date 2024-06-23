package faewulf.antiraidfarm.utils;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

public class RaidData {
    public static void setData(IPlayerDataSaver player, Long Data) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putLong("lastRaid", Data);
    }

    public static Long getData(IPlayerDataSaver player) {
        NbtCompound nbt = player.getPersistentData();

        if (nbt.contains("lastRaid", NbtElement.LONG_TYPE))
            return nbt.getLong("lastRaid");
        else
            return -1L;
    }

    public static void resetData(IPlayerDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putLong("lastRaid", -1L);
    }
}
