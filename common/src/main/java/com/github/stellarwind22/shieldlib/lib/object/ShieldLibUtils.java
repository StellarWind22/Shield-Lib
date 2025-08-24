package com.github.stellarwind22.shieldlib.lib.object;

import com.github.stellarwind22.shieldlib.lib.component.ShieldInformation;
import com.github.stellarwind22.shieldlib.lib.component.ShieldLibDataComponents;
import com.github.stellarwind22.shieldlib.lib.config.ShieldLibConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Everything in this class will remain binary-compatible to the maximum extent.
 */
public class ShieldLibUtils {

    public static final BlocksAttacks VANILLA_SHIELD_BLOCKS_ATTACKS_COMPONENT =
            new BlocksAttacks(
                    0.25F,
                    1.0F,
                    List.of(new BlocksAttacks.DamageReduction(ShieldLibConfig.vanilla_shield_blocking_angle, Optional.empty(), 0.0F, 1.0F)),
                    new BlocksAttacks.ItemDamageFunction(3.0F, 1.0F, 1.0F),
                    Optional.of(DamageTypeTags.BYPASSES_SHIELD),
                    Optional.of(SoundEvents.SHIELD_BLOCK),
                    Optional.of(SoundEvents.SHIELD_BREAK)
            );

    public static final BlocksAttacks TOWER_SHIELD_BLOCKS_ATTACKS_COMPONENT =
            new BlocksAttacks(
                    0.25F,
                    1.0F,
                    List.of(new BlocksAttacks.DamageReduction(ShieldLibConfig.tower_blocking_angle, Optional.empty(), 0.0F, 1.0F)),
                    new BlocksAttacks.ItemDamageFunction(3.0F, 1.0F, 1.0F),
                    Optional.of(DamageTypeTags.BYPASSES_SHIELD),
                    Optional.of(SoundEvents.SHIELD_BLOCK),
                    Optional.of(SoundEvents.SHIELD_BREAK)
            );

    public static final ShieldInformation TOWER_SHIELD_INFORMATION_COMPONENT = new ShieldInformation("tower", List.of("none"));
    public static final ShieldInformation SPIKED_TOWER_SHIELD_INFORMATION_COMPONENT = new ShieldInformation("tower", List.of("spiked"));

    public static final BlocksAttacks BUCKLER_SHIELD_BLOCKS_ATTACKS_COMPONENT =
            new BlocksAttacks(
                    0.25F,
                    1.0F,
                    List.of(new BlocksAttacks.DamageReduction(ShieldLibConfig.buckler_blocking_angle, Optional.empty(), 0.0F, 1.0F)),
                    new BlocksAttacks.ItemDamageFunction(3.0F, 1.0F, 1.0F),
                    Optional.of(DamageTypeTags.BYPASSES_SHIELD),
                    Optional.of(SoundEvents.SHIELD_BLOCK),
                    Optional.of(SoundEvents.SHIELD_BREAK)
            );

    public static final ShieldInformation BUCKLER_SHIELD_INFORMATION_COMPONENT = new ShieldInformation("buckler", List.of("none"));
    public static final ShieldInformation SPIKED_BUCKLER_SHIELD_INFORMATION_COMPONENT = new ShieldInformation("buckler", List.of("spiked"));

    public static final BlocksAttacks HEATER_SHIELD_BLOCKS_ATTACKS_COMPONENT =
            new BlocksAttacks(
                    0.25F,
                    1.0F,
                    List.of(new BlocksAttacks.DamageReduction(ShieldLibConfig.heater_blocking_angle, Optional.empty(), 0.0F, 1.0F)),
                    new BlocksAttacks.ItemDamageFunction(3.0F, 1.0F, 1.0F),
                    Optional.of(DamageTypeTags.BYPASSES_SHIELD),
                    Optional.of(SoundEvents.SHIELD_BLOCK),
                    Optional.of(SoundEvents.SHIELD_BREAK)
            );

    public static final ShieldInformation HEATER_SHIELD_INFORMATION_COMPONENT = new ShieldInformation("heater", List.of("none"));
    public static final ShieldInformation SPIKED_HEATER_SHIELD_INFORMATION_COMPONENT = new ShieldInformation("heater", List.of("spiked"));

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

    public static Item.Properties towerShieldProperties(Item.Properties properties, int cooldownTicks) {
        return defaultShieldProperties(properties
                .component(DataComponents.BLOCKS_ATTACKS, withCooldownTicks(TOWER_SHIELD_BLOCKS_ATTACKS_COMPONENT, cooldownTicks))
                .component(ShieldLibDataComponents.SHIELD_INFORMATION.get(), TOWER_SHIELD_INFORMATION_COMPONENT
                )
        );
    }

    public static Item.Properties bucklerShieldProperties(Item.Properties properties, int cooldownTicks) {
        return defaultShieldProperties(properties
                .component(DataComponents.BLOCKS_ATTACKS, withCooldownTicks(BUCKLER_SHIELD_BLOCKS_ATTACKS_COMPONENT, cooldownTicks))
                .component(ShieldLibDataComponents.SHIELD_INFORMATION.get(), BUCKLER_SHIELD_INFORMATION_COMPONENT
                )
        );
    }

    public static Item.Properties heaterShieldProperties(Item.Properties properties, int cooldownTicks) {
        return defaultShieldProperties(properties
                .component(DataComponents.BLOCKS_ATTACKS, withCooldownTicks(HEATER_SHIELD_BLOCKS_ATTACKS_COMPONENT, cooldownTicks))
                .component(ShieldLibDataComponents.SHIELD_INFORMATION.get(), HEATER_SHIELD_INFORMATION_COMPONENT
                )
        );
    }

    public static Item.Properties spikedTowerShieldProperties(Item.Properties properties, int cooldownTicks) {
        return defaultShieldProperties(properties
                .component(DataComponents.BLOCKS_ATTACKS, withCooldownTicks(TOWER_SHIELD_BLOCKS_ATTACKS_COMPONENT, cooldownTicks))
                .component(ShieldLibDataComponents.SHIELD_INFORMATION.get(), SPIKED_TOWER_SHIELD_INFORMATION_COMPONENT
                )
        );
    }

    public static Item.Properties spikedBucklerShieldProperties(Item.Properties properties, int cooldownTicks) {
        return defaultShieldProperties(properties
                .component(DataComponents.BLOCKS_ATTACKS, withCooldownTicks(BUCKLER_SHIELD_BLOCKS_ATTACKS_COMPONENT, cooldownTicks))
                .component(ShieldLibDataComponents.SHIELD_INFORMATION.get(), SPIKED_BUCKLER_SHIELD_INFORMATION_COMPONENT
                )
        );
    }

    public static Item.Properties spikedHeaterShieldProperties(Item.Properties properties, int cooldownTicks) {
        return defaultShieldProperties(properties
                .component(DataComponents.BLOCKS_ATTACKS, withCooldownTicks(HEATER_SHIELD_BLOCKS_ATTACKS_COMPONENT, cooldownTicks))
                .component(ShieldLibDataComponents.SHIELD_INFORMATION.get(), SPIKED_HEATER_SHIELD_INFORMATION_COMPONENT
                )
        );
    }

    public static Item.Properties defaultShieldProperties(Item.Properties properties) {
        return properties
                .equippableUnswappable(EquipmentSlot.OFFHAND)
                .component(DataComponents.BREAK_SOUND, SoundEvents.SHIELD_BREAK);
    }

    @SuppressWarnings("unused")
    public static Item.Properties withShieldComponent(Item.Properties properties, BlocksAttacks blocksAttacks) {
        return properties
                .equippableUnswappable(EquipmentSlot.OFFHAND)
                .component(DataComponents.BLOCKS_ATTACKS, blocksAttacks);
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

    @SuppressWarnings("unused")
    public static BlocksAttacks withHorizontalAngle(BlocksAttacks in, float angle) {

        List<BlocksAttacks.DamageReduction> reductions = new ArrayList<>(in.damageReductions());
        List<BlocksAttacks.DamageReduction> newReductions = new ArrayList<>();

        for(BlocksAttacks.DamageReduction reduction : reductions) {
            newReductions.add(
                    new BlocksAttacks.DamageReduction(
                            angle,
                            reduction.type(),
                            reduction.base(),
                            reduction.factor()
                    )
            );
        }

        return new BlocksAttacks(
                in.blockDelaySeconds(),
                in.blockDelaySeconds(),
                newReductions,
                in.itemDamage(),
                in.bypassedBy(),
                in.blockSound(),
                in.disableSound()
        );
    }

    public static int getEnchantmentLevel(ResourceLocation enchantmentId, ItemStack itemStack) {
        ItemEnchantments enchants = itemStack.getEnchantments();
        AtomicInteger result = new AtomicInteger();

        for (Holder<Enchantment> holder : enchants.keySet()) {

            holder.unwrapKey().ifPresent(key -> {
                if(key.location().equals(enchantmentId)) {
                    result.set(enchants.getLevel(holder));
                }
            });
        }
        return result.get();
    }
}
