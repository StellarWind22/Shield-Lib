package com.github.stellarwind22.shieldlib.lib.object;

import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlocksAttacks;

import java.util.List;
import java.util.Optional;

/**
 * Everything in this class will remain binary-compatible to the maximum extent.
 */
public class ShieldLibUtils {

    public static final int VANILLA_SHIELD_DURABILITY = 336;
    public static final int VANILLA_SHIELD_COOLDOWN_TICKS = 100;
    public static final int VANILLA_SHIELD_ENCHANTABILITY = 14;
    public static final BlocksAttacks VANILLA_SHIELD_BLOCKS_ATTACKS_COMPONENT =
            new BlocksAttacks(
                    0.25F,
                    1.0F,
                    List.of(new BlocksAttacks.DamageReduction(90.0F, Optional.empty(), 0.0F, 1.0F)),
                    new BlocksAttacks.ItemDamageFunction(3.0F, 1.0F, 1.0F),
                    Optional.of(DamageTypeTags.BYPASSES_SHIELD),
                    Optional.of(SoundEvents.SHIELD_BLOCK),
                    Optional.of(SoundEvents.SHIELD_BREAK)
            );

    /**
     * @param itemStack stack to check.
     * @return true if itemStack can be considered a shield.
     */
    public static boolean isShieldItem(ItemStack itemStack) {
        return itemStack.has(DataComponents.BLOCKS_ATTACKS);
    }

    /**
     * @param item item to check
     * @return true if item can be considered a shield.
     */
    public static boolean isShieldItem(Item item) {
        return item.components().has(DataComponents.BLOCKS_ATTACKS);
    }

    /**
     * @param itemStack stack to check.
     * @return true if the item (a shield) can support banner.
     */
    public static boolean supportsBanner(ItemStack itemStack) {
        return supportsBanner(itemStack.getItem());
    }

    /**
     * @param item item to check
     * @return true if the item (a shield) can support banner.
     */
    @SuppressWarnings("deprecation")
    public static boolean supportsBanner(Item item) {
        return item.builtInRegistryHolder().is(ShieldLibTags.SUPPORTS_BANNER);
    }

    public static Item.Properties vanillaShieldProperties(Item.Properties properties) {
        return properties
                .equippableUnswappable(EquipmentSlot.OFFHAND)
                .component(DataComponents.BREAK_SOUND, SoundEvents.SHIELD_BREAK);
    }

    public static Item.Properties defaultShieldProperties(Item.Properties properties) {
        return properties
                .equippableUnswappable(EquipmentSlot.OFFHAND)
                .component(DataComponents.BREAK_SOUND, SoundEvents.SHIELD_BREAK);
    }

    public static BlocksAttacks withCooldownTicks(int cooldownTicks) {
        return withCooldownTicks(VANILLA_SHIELD_BLOCKS_ATTACKS_COMPONENT, cooldownTicks);
    }

    /**
     * @param in BlocksAttacks component going in.
     * @param cooldownTicks cooldown ticks to be added.
     * @return component with that many cooldown ticks.
     */
    public static BlocksAttacks withCooldownTicks(BlocksAttacks in, int cooldownTicks) {
        return new BlocksAttacks(
                in.blockDelaySeconds(),
                (float)cooldownTicks / 100.F,
                in.damageReductions(),
                in.itemDamage(),
                in.bypassedBy(),
                in.blockSound(),
                in.disableSound()
        );
    }
}
