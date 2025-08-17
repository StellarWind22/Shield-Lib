package com.github.stellarwind22.shieldlib.mixin;

import com.github.stellarwind22.shieldlib.lib.config.ShieldConfigUtil;
import com.github.stellarwind22.shieldlib.lib.event.ShieldDisabledEvent;
import com.github.stellarwind22.shieldlib.lib.event.ShieldPreDisableEvent;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlocksAttacks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(
            at = @At(
                    value = "INVOKE",
                    shift = At.Shift.AFTER,
                    target = "Lnet/minecraft/world/entity/LivingEntity;blockUsingItem(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;)V"
            ),
            method = "blockUsingItem(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;)V",
            cancellable = true
    )
    private void blockUsingItemHookAfterCallSuper(ServerLevel level, LivingEntity attacker, CallbackInfo cb) {

        Player player = (Player) (Object) this;

        if(ShieldConfigUtil.universalDisable()) {

            //Disable all shields
            float blockForSeconds = attacker.getSecondsToDisableBlocking();

            if(blockForSeconds <= 0.0F) {
                return;
            }

            for (ItemStack itemStack : player.getInventory()) {
                BlocksAttacks blocksAttacks = itemStack != null ? itemStack.get(DataComponents.BLOCKS_ATTACKS): null;

                if(blocksAttacks != null) {
                    ShieldPreDisableEvent.EVENT.invoker().onPreDisable(level, attacker, player, player.getUsedItemHand(), itemStack);
                    blocksAttacks.disable(level, player, blockForSeconds, itemStack);
                }
            }

            cb.cancel();
        }
    }

    @Inject(
            at = @At(
                    value = "INVOKE",
                    shift = At.Shift.AFTER,
                    target = "Lnet/minecraft/world/item/component/BlocksAttacks;disable(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;FLnet/minecraft/world/item/ItemStack;)V"
            ),
            method = "blockUsingItem(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;)V",
            locals = LocalCapture.NO_CAPTURE
    )
    private void blockUsingItemHookAfterApplyShieldCooldown(ServerLevel level, LivingEntity attacker, CallbackInfo cb, @Local(ordinal = 0) ItemStack itemStack) {
        Player player = (Player) (Object) this;
        ShieldDisabledEvent.EVENT.invoker().onDisable(level, attacker, player, player.getUsedItemHand(), itemStack);
    }
}
