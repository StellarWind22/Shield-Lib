package com.github.stellarwind22.shieldlib.mixin;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.lib.config.ShieldLibConfig;
import com.github.stellarwind22.shieldlib.lib.event.ShieldEvents;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibTags;
import dev.architectury.event.EventResult;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
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
            method = "applyItemBlocking(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)F",
            cancellable = true
    )
    private void applyItemBlockingReturn(ServerLevel level, DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        ItemStack blockingItem = self.getItemBlockingWith();
        InteractionHand hand = self.getUsedItemHand();

        EventResult result = ShieldEvents.CAN_BLOCK.invoker().tryBlock(level, self, source, amount, hand, blockingItem);

        if(result != EventResult.pass()) {
            ShieldEvents.BLOCK_FAIL.invoker().onFail(level, self, source, amount, hand, blockingItem);
            cir.setReturnValue(0F);
        }

        ShieldEvents.BLOCK.invoker().onBlock(level, self, source, amount, hand, blockingItem);
    }

    @Inject(
            at = @At("HEAD"),
            method = "blockUsingItem",
            cancellable = true
    )
    private void blockUsingItem(ServerLevel level, LivingEntity attacker, CallbackInfo ci) {

        LivingEntity self = (LivingEntity) (Object) this;
        boolean isPlayer = self instanceof Player;

        if(isPlayer) {

            ItemStack shield = self.getItemBlockingWith();
            BlocksAttacks blocksAttacks = shield != null ? shield.get(DataComponents.BLOCKS_ATTACKS) : null;

            float secondsToDisable = attacker.getSecondsToDisableBlocking();
            secondsToDisable = ShieldLib.getCooldownSecondsWithModifiers((Player) self, shield, blocksAttacks, secondsToDisable);

            if (secondsToDisable > 0 && blocksAttacks != null) {

                ShieldEvents.DISABLE.invoker().onDisable(level, attacker, self, isPlayer, self.getUsedItemHand(), shield, secondsToDisable);

                if(ShieldLibConfig.universal_disabling) {
                    Iterable<Holder<Item>> holders = BuiltInRegistries.ITEM.getTagOrEmpty(ShieldLibTags.C_SHIELD);

                    for(Holder<Item> holder : holders) {
                        blocksAttacks.disable(level, self, secondsToDisable, new ItemStack(holder.value()));
                    }

                    ci.cancel();

                } else {
                    blocksAttacks.disable(level, self, secondsToDisable, shield);
                    ci.cancel();
                }
            }
        }
    }

    @Inject(at = @At("TAIL"), method = "getSecondsToDisableBlocking", cancellable = true)
    private void secondsToDisableBlocking(CallbackInfoReturnable<Float> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        float cooldown = ShieldLib.getCooldownSeconds(self.level().registryAccess(), self.getWeaponItem());

        if(cooldown != cir.getReturnValue()) {
            cir.setReturnValue(cooldown);
        }
    }
}
