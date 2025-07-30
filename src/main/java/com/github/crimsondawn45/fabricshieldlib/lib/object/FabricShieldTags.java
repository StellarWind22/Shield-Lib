package com.github.crimsondawn45.fabricshieldlib.lib.object;

import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLib;
import com.github.crimsondawn45.fabricshieldlib.mixin.RecipeMixin;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

/**
 * Everything in this class will remain binary-compatible to the maximum extent.
 */
public class FabricShieldTags {
	/**
	 * Indicate if a shield supports banners or not.
	 * <p>
	 * Add your modded shield to this tag if it supports banners.
	 * <p>
	 * {@link RecipeMixin} covers includes all shields in this tag into {@link net.minecraft.recipe.ShieldDecorationRecipe}
	 */
	public static final TagKey<Item> SUPPORTS_BANNER =
			TagKey.of(RegistryKeys.ITEM, Identifier.of(FabricShieldLib.MOD_ID, "supports_banner"));

	/**
	 * Explicitly disable advanced tooltip for a given set of shields.
	 * <p>
	 * FabricShieldLib will add an advanced tooltip to indicate the cooling time when disabled by an axe.
	 * <p>
	 * By default, this tooltip is added to shields, including both vanilla and modded.
	 * The global switch is {@link com.github.crimsondawn45.fabricshieldlib.lib.config.FabricShieldLibConfig#enable_tooltips}.
	 * If a shield is not in this tag, the advanced tooltip will be visible.
	 * <p>
	 * Replaces `FabricShield::displayTooltip`
	 */
	public static final TagKey<Item> NO_TOOLTIP =
			TagKey.of(RegistryKeys.ITEM, Identifier.of(FabricShieldLib.MOD_ID, "no_tooltip"));
}
