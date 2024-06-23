package faewulf.antiraidfarm.mixin;

import com.mojang.authlib.GameProfile;
import faewulf.antiraidfarm.AntiRaidfarm;
import faewulf.antiraidfarm.utils.IPlayerDataSaver;
import faewulf.antiraidfarm.utils.RaidData;
import faewulf.antiraidfarm.utils.converter;
import faewulf.antiraidfarm.utils.permissions;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    @Shadow
    public abstract ServerWorld getServerWorld();

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tickInject(CallbackInfo ci) {

        //if player has by pass permission, then cancel this method
        if (Permissions.check(this, permissions.BY_PASS, 3)) {
            return;
        }

        boolean criteria = false;

        for (ItemStack itemStack : this.getHandItems()) {
            if (itemStack.getItem() == Items.OMINOUS_BOTTLE) {
                criteria = true;
                break;
            }
        }

        if (!criteria)
            return;

        Long lastRaidTime = RaidData.getData((IPlayerDataSaver) this);

        //if no cooldown
        if (lastRaidTime == null || lastRaidTime == -1L)
            return;

        //get current local time and player's timeStamp
        Long currentWorldTime = this.getServerWorld().getTime();

        long diff = currentWorldTime - lastRaidTime;
        //check if > config's delay

        //config's timer
        int time = AntiRaidfarm.getConfig().waitTime * 20;

        //if cooldown then return true
        if (diff < time) {
            String timeLeft = converter.convertTicksToTime(time - diff);
            Text message = Text.literal("You can start another raid after " + timeLeft);
            this.sendMessage(message, true);
        }
    }
}
