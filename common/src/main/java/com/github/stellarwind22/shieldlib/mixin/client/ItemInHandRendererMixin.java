package com.github.stellarwind22.shieldlib.mixin.client;

import com.github.stellarwind22.shieldlib.lib.object.ShieldLibUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @WrapOperation(
            method = "renderArmWithItem",
            constant = @Constant(classValue = ShieldItem.class)
    )
    private boolean wrapInstanceCheck(Object instance, Operation<Boolean> original) {
        Item item = (Item) instance;
        return original.call(instance) || ShieldLibUtils.isShieldItem(item);
    }
}
