package com.github.stellarwind22.shieldlib.lib.object;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ShieldLibTags {

    /**
     * Indicate if a shield supports banners for not.
     * <p>
     * Add your modded shield to this tag if it supports banners.
     * </p>
     */
    public static TagKey<Item> SUPPORTS_BANNER;

    /**
     * Indicate if a shield's advanced tooltip is disabled.
     * <p>
     *     ShieldLib will add an advanced tooltip to indicate the cooldown when hit by axe.
     * </p>
     * <p>
     * Add your modded shield to disabled advanced tooltip.
     * </p>
     */
    public static TagKey<Item> NO_TOOLTIP;

    /**
     * Indicate if an item supports shield enchantments
     */
    public static TagKey<Item> SHIELD_ENCHANTABLE;

    public static void init() {
        SUPPORTS_BANNER =       TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "supports_banner"));
        NO_TOOLTIP =            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "no_tooltip"));
        SHIELD_ENCHANTABLE =    TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "enchantable/shield"));
    }
}
