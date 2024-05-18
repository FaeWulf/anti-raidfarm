package faewulf.antiraidfarm.mixin;

import faewulf.antiraidfarm.utils.IPlayerDataSaver;
import faewulf.antiraidfarm.utils.RaidData;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.village.raid.Raid;

import java.time.LocalDateTime;

//this mixins is for storing timeStamp into player's data after they trigger a raid
@Mixin(Raid.class)
public class RaidMixin {
    @Inject(method = "start", at = @At("RETURN"))
    private void start(ServerPlayerEntity serverPlayerEntity, CallbackInfoReturnable<Boolean> cir) {
        Logger LOGGER = LoggerFactory.getLogger("anti-raidfarm");

        if (cir.getReturnValue()) {
            //set timestamp
            LocalDateTime myObj = LocalDateTime.now();
            LOGGER.info("A raid has started by: {} at {}, set delay timer", serverPlayerEntity.getName(), myObj.toString());
            RaidData.setData((IPlayerDataSaver) serverPlayerEntity, myObj.toString());
        }
    }
}
