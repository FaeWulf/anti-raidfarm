package faewulf.antiraidfarm.mixin;

import faewulf.antiraidfarm.utils.IPlayerDataSaver;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class PlayerDataSaver implements IPlayerDataSaver {
    @Unique
    private NbtCompound persistentData;

    @Override
    public NbtCompound getPersistentData() {
        if (this.persistentData == null) {
            this.persistentData = new NbtCompound();
        }

        return this.persistentData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void writeNbt(NbtCompound nbt, CallbackInfoReturnable info) {
        if (persistentData != null) {
            nbt.put("faewulf.antiraidfarm", persistentData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void readNbt(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("faewulf.antiraidfarm", 10)) {
            persistentData = nbt.getCompound("faewulf.antiraidfarm");
        }
    }
}
