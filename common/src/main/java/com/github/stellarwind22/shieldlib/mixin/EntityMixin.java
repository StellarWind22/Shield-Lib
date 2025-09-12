package com.github.stellarwind22.shieldlib.mixin;

import com.github.stellarwind22.shieldlib.lib.event.ShieldEvents;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibUtils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(at = @At("TAIL"), method = "push(Lnet/minecraft/world/entity/Entity;)V")
    private void push(Entity other, CallbackInfo ci) {
        Entity self = (Entity) (Object) this;

        if (self instanceof Player player && player.isBlocking()) {
            ItemStack shield = player.getUseItem();

            if (ShieldLibUtils.isShieldItem(shield)) {
                BlocksAttacks blocksAttacks = shield.get(DataComponents.BLOCKS_ATTACKS);
                if (blocksAttacks == null) return;

                if (!player.level().isClientSide() && player.level() instanceof ServerLevel serverLevel) {

                    Vec3 otherPos = other.position();
                    double angle;
                    if(otherPos != null) {
                        Vec3 viewVector = self.calculateViewVector(0.0F, self.getYHeadRot());
                        Vec3 difference = otherPos.subtract(self.position());
                        difference = (new Vec3(difference.x, 0.0F, difference.z)).normalize();
                        angle = Math.acos(difference.dot(viewVector));
                    } else {
                        angle = Math.PI;
                    }

                    float resolved = blocksAttacks.resolveBlockedDamage(player.damageSources().playerAttack(player), 100.0F, angle);
                    boolean withinAngle = resolved > 0;
                    ShieldEvents.COLLIDE.invoker().onCollide(serverLevel, player, other, withinAngle, player.getUsedItemHand(), shield);
                }
            }
        }
    }
}

