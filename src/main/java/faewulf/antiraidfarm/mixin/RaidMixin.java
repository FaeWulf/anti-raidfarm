package faewulf.antiraidfarm.mixin;

import faewulf.antiraidfarm.AntiRaidfarm;
import faewulf.antiraidfarm.utils.IPlayerDataSaver;
import faewulf.antiraidfarm.utils.RaidData;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.village.raid.Raid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//this mixins is for storing timeStamp into player's data after they trigger a raid
@Mixin(Raid.class)
public class RaidMixin {
    @Inject(method = "start", at = @At("RETURN"))
    private void start(ServerPlayerEntity serverPlayerEntity, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            //set timestamp
            Long currentWorldTime = serverPlayerEntity.getWorld().getTime();
            AntiRaidfarm.LOGGER.info("A raid has started by: {} at {}, set delay timer", serverPlayerEntity.getName(), currentWorldTime);
            RaidData.setData((IPlayerDataSaver) serverPlayerEntity, currentWorldTime);
        }
    }
}
