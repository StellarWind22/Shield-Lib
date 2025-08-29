package com.github.stellarwind22.shieldlib.mixin;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.lib.config.ShieldLibConfig;
import com.github.stellarwind22.shieldlib.lib.event.ShieldEvents;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibTags;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlocksAttacks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(
            at = @At(
                    value = "INVOKE",
                    shift = At.Shift.AFTER,
                    target = "Lnet/minecraft/world/item/component/BlocksAttacks;resolveBlockedDamage(Lnet/minecraft/world/damagesource/DamageSource;FD)F"
            ),
            method = "applyItemBlocking(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)F"
    )
    private void applyItemBlocking(ServerLevel level, DamageSource source, float amount, CallbackInfoReturnable<Float> cb, @Local(ordinal = 0) ItemStack blockingItem) {
        LivingEntity defender = (LivingEntity) (Object) this;
        ShieldEvents.BLOCK.invoker().onBlock(level, defender, source, amount, defender.getUsedItemHand(), blockingItem);
    }

    @Inject(
            at = @At("HEAD"),
            method = "blockUsingItem",
            cancellable = true
    )
    private void blockUsingItem(ServerLevel level, LivingEntity attacker, CallbackInfo ci) {

        LivingEntity defender = (LivingEntity) (Object) this;
        boolean isPlayer = defender instanceof Player;

        if(isPlayer) {

            ItemStack shield = defender.getItemBlockingWith();
            BlocksAttacks blocksAttacks = shield != null ? shield.get(DataComponents.BLOCKS_ATTACKS) : null;

            float secondsToDisable = attacker.getSecondsToDisableBlocking();
            secondsToDisable = ShieldLib.getCooldownSecondsWithModifiers((Player) defender, shield, blocksAttacks, secondsToDisable);

            if (secondsToDisable > 0 && blocksAttacks != null) {

                ShieldEvents.DISABLE.invoker().onDisable(level, attacker, defender, isPlayer, defender.getUsedItemHand(), shield, secondsToDisable);

                if(ShieldLibConfig.universal_disabling) {
                    Iterable<Holder<Item>> holders = BuiltInRegistries.ITEM.getTagOrEmpty(ShieldLibTags.C_SHIELD);

                    for(Holder<Item> holder : holders) {
                        blocksAttacks.disable(level, defender, secondsToDisable, new ItemStack(holder.value()));
                    }

                    ci.cancel();

                } else {
                    blocksAttacks.disable(level, defender, secondsToDisable, shield);
                    ci.cancel();
                }
            }
        }
    }
}
