package faewulf.antiraidfarm.mixin;

import faewulf.antiraidfarm.AntiRaidfarm;
import faewulf.antiraidfarm.utils.IPlayerDataSaver;
import faewulf.antiraidfarm.utils.RaidData;
import faewulf.antiraidfarm.utils.permissions;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import java.time.Duration;
import java.time.LocalDateTime;

//Inject into BadOmenStatusEffect class
@Mixin(targets = {"net.minecraft.entity.effect.BadOmenStatusEffect"})
public class AntiRaidFarmMixin {
    @Inject(at = @At("HEAD"), method = "tryStartRaid", cancellable = true)
    private void tryStartRaid(ServerPlayerEntity player, ServerWorld world, CallbackInfoReturnable<Boolean> info) {

        //get player nbt data of the mod syste
        String date = RaidData.getData((IPlayerDataSaver) player);

        //if player has bu pass permission, then cancel this method
        if (Permissions.check(player, permissions.BY_PASS)) {
            return;
        }


        if (!date.isEmpty()) {
            //get current local time and player's timeStamp
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(LocalDateTime.parse(date), now);

            long diff = duration.getSeconds();
            //check if > config's delay

            //config's timer
            int time = AntiRaidfarm.getConfig().waitTime;

            System.out.println(time);

            //if cooldown then return true
            if (diff < time) {
                //cancel method

                //convert time left to human readable time format
                long timeLeft = time - diff;
                String readableTime = String.format("%dm%02ds", timeLeft / 60, timeLeft % 60);

                //message to send to the player
                Text message = Text.literal("You can start another raid after " + readableTime);
                player.sendMessage(message, true);

                //injects
                info.setReturnValue(true);
                info.cancel();
            }
        }

    }
}