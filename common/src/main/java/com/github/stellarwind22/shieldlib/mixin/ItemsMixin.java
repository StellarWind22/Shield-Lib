package com.github.stellarwind22.shieldlib.mixin;

import com.github.stellarwind22.shieldlib.lib.config.ShieldConfigUtil;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Items.class)
public class ItemsMixin {

    @ModifyExpressionValue(
            method = "<clinit>",
            slice = @Slice(
                    from = @At(value = "CONSTANT", args = "stringValue=shield"),
                    to = @At(value = "FIELD", opcode = Opcodes.PUTSTATIC, target = "Lnet/minecraft/world/item/Items;SHIELD:Lnet/minecraft/world/item/Item;")
            ),
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;equippableUnswappable(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/Item$Properties;")
    )
    private static Item.Properties assignVanillaShieldEnchantability(Item.Properties properties) {
        return properties.enchantable(ShieldConfigUtil.vanillaShieldEnchantability());
    }
}
