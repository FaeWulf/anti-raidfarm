package faewulf.antiraidfarm.mixin;

import faewulf.antiraidfarm.AntiRaidfarm;
import faewulf.antiraidfarm.utils.IPlayerDataSaver;
import faewulf.antiraidfarm.utils.RaidData;
import faewulf.antiraidfarm.utils.converter;
import faewulf.antiraidfarm.utils.permissions;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//Inject into BadOmenStatusEffect class
@Mixin(targets = {"net.minecraft.entity.effect.BadOmenStatusEffect"})
public class AntiRaidFarmMixin {

    @Inject(
            method = "applyUpdateEffect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;)Z",
                    shift = At.Shift.BEFORE
            )
            , cancellable = true
    )
    private void applyUpdateEffectInject(LivingEntity entity, int amplifier, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof ServerPlayerEntity serverPlayerEntity) {
            //if player has by pass permission, then cancel this method
            if (Permissions.check(serverPlayerEntity, permissions.BY_PASS, 3)) {
                return;
            }

            Long lastRaidTime = RaidData.getData((IPlayerDataSaver) serverPlayerEntity);

            //if no cooldown
            if (lastRaidTime == null || lastRaidTime == -1L)
                return;

            //get current local time and player's timeStamp
            Long currentWorldTime = serverPlayerEntity.getServerWorld().getTime();

            long diff = currentWorldTime - lastRaidTime;
            //check if > config's delay

            //config's timer
            int time = AntiRaidfarm.getConfig().waitTime * 20;

            //if cooldown then return true
            if (diff < time) {
                //convert time left to human readable time format
                String timeLeft = converter.convertTicksToTime(time - diff);

                //message to send to the player
                Text message = Text.literal("You can start another raid after " + timeLeft);
                serverPlayerEntity.sendMessage(message, true);

                //injects
                cir.setReturnValue(true);
                cir.cancel();
            } else
                RaidData.resetData((IPlayerDataSaver) serverPlayerEntity);
        }
    }
}