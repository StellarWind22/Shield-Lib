package com.github.stellarwind22.shieldlib.mixin;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {

    @Inject(at = @At("RETURN"), method = "modifyInput", cancellable = true)
    private void modifyInput(Vec2 vec2, CallbackInfoReturnable<Vec2> cir) {
       Player player = (Player) (Object) this;
       ItemStack stack = player.getUseItem();

       if(stack.has(DataComponents.BLOCKS_ATTACKS)) {
           cir.setReturnValue(ShieldLib.getMovementWithModifiers(player, stack, stack.get(DataComponents.BLOCKS_ATTACKS), cir.getReturnValue()));
       }
    }
}
