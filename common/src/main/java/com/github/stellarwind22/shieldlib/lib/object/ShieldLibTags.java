package com.github.stellarwind22.shieldlib.lib.object;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ShieldLibTags {

    /**
     * Common axes item tag c:tools/axe
     */
    public static TagKey<Item> C_AXE;

    /**
     * Common axes item tag c:tools/axes
     */
    public static TagKey<Item> C_AXES;

    /**
     * Common shields item tag c:tools/shield
     */
    public static TagKey<Item> C_SHIELD;

    /**
     * Common shields item tag c:tools/shields
     */
    public static TagKey<Item> C_SHIELDS;

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
        C_AXE =              TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/axe"));
        C_AXES =              TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/axes"));
        C_SHIELD =              TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/shield"));
        C_SHIELDS =              TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/shields"));
        SUPPORTS_BANNER =       TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "supports_banner"));
        NO_TOOLTIP =            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "no_tooltip"));
        SHIELD_ENCHANTABLE =    TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "enchantable/shield"));
    }
}
