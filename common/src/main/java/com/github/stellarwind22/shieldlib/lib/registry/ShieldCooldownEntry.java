package com.github.stellarwind22.shieldlib.lib.registry;

import com.github.stellarwind22.shieldlib.lib.component.ShieldDataComponents;
import com.github.stellarwind22.shieldlib.lib.component.ShieldInformation;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibUtils;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Supplier;

public record ShieldCooldownEntry(ShieldInformation shieldInformation, TagKey<Item> tag, Supplier<Float> cooldown) {

    @SuppressWarnings("unused")
    public boolean matchShield(ItemStack stack) {

        ShieldInformation stackShieldInformation;

        //Override for vanilla
        if(stack.is(Items.SHIELD)) {

            stackShieldInformation = ShieldLibUtils.VANILLA_SHIELD_INFORMATION_COMPONENT;

        } else {
            //Grab information component safely
            if(!stack.has(ShieldDataComponents.SHIELD_INFORMATION.get())) return false;
            stackShieldInformation = stack.get(ShieldDataComponents.SHIELD_INFORMATION.get());
            assert stackShieldInformation != null;
        }

        return matchShield(stackShieldInformation);
    }

    public boolean matchShield(ShieldInformation stackShieldInformation) {
        return stackShieldInformation.isType(shieldInformation.type()) && stackShieldInformation.hasFeatures(shieldInformation.features());
    }

    public boolean matchWeapon(RegistryAccess access, ItemStack weapon) {
        return access.lookup(Registries.ITEM).orElseThrow().getOrThrow(tag).stream().anyMatch((itemHolder -> itemHolder.value().equals(weapon.getItem())));
    }
}
