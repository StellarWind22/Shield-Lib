package com.github.stellarwind22.shieldlib.mixin;

import com.github.stellarwind22.shieldlib.lib.event.ShieldTooltipEvent;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(at = @At("RETURN"), method = "getTooltipLines")
    private void addDetailsToTooltip(Item.TooltipContext tooltipContext, Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir) {
        ItemStack instance = (ItemStack) (Object) this;

        //Only add tooltips to shields
        if(ShieldLibUtils.isShieldItem(instance)) {
           ShieldTooltipEvent.EVENT.invoker().OnGetTooltip(player, instance, tooltipContext, tooltipFlag, cir.getReturnValue());
        }
    }
}
