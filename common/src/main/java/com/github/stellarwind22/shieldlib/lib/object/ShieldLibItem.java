package com.github.stellarwind22.shieldlib.lib.object;

import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Repairable;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.stream.Collectors;

// Based on @link{net/minecraft/world/item/ShieldItem}
public class ShieldLibItem extends Item {

    /**
     * @param properties        item properties.
     * @param cooldownTicks     ticks shield will be in cooldown after disabled.
     * @param enchantability    enchantability of shield. Vanilla: 14
     * @param repairItems       item(s) for repairing shield.
     */
    @SuppressWarnings("deprecation")
    public ShieldLibItem(Properties properties, int cooldownTicks, int enchantability, Item... repairItems) {
        this(properties, cooldownTicks, enchantability,
                HolderSet.direct(
                        Arrays.stream(repairItems).map(Item::builtInRegistryHolder).collect(Collectors.toList())
                )
        );


    }

    /**
     * @param properties    item properties
     * @param cooldownTicks ticks shield will be disabled for after disabled.
     * @param material      tool material.
     */
    public ShieldLibItem(Properties properties, int cooldownTicks, ToolMaterial material) {
        this(
                properties.durability(material.durability()),
                cooldownTicks,
                material.enchantmentValue(),
                material.repairItems()
        );
    }

    /**
     * @param properties        item properties.
     * @param cooldownTicks     ticks shield will be in cooldown after disabled.
     * @param enchantability    enchantability of shield. Vanilla: 14
     * @param repairItemTag     item tag for repairing shield.
     */
    public ShieldLibItem(Properties properties, int cooldownTicks, int enchantability, TagKey<Item> repairItemTag) {
        this(
                properties,
                cooldownTicks,
                enchantability,
                BuiltInRegistries
                        .acquireBootstrapRegistrationLookup(BuiltInRegistries.ITEM)
                        .getOrThrow(repairItemTag)
        );
    }

    /**
     * @param properties        item properties.
     * @param cooldownTicks     ticks shield will be in cooldown after disabled.
     * @param enchantability    enchantability of shield. Vanilla: 14
     * @param repairItems       list of items/tags for repairing shield.
     */
    public ShieldLibItem(Properties properties, int cooldownTicks, int enchantability, @Nullable HolderSet<Item> repairItems) {
        super(attachRepairable(ShieldLibUtils.defaultShieldProperties(properties), repairItems)
                .enchantable(enchantability)
                .component(DataComponents.BLOCKS_ATTACKS,
                        ShieldLibUtils.withCooldownTicks(
                                ShieldLibUtils.VANILLA_SHIELD_BLOCKS_ATTACKS_COMPONENT,
                                cooldownTicks
                        )
                )
        );
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity user) {
        return 72000;
    }

    @Override
    public @NotNull InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    public static Item.Properties attachRepairable(Item.Properties properties, @Nullable HolderSet<Item> repairItems) {
        return (repairItems == null ? properties
                : properties.component(DataComponents.REPAIRABLE, new Repairable(repairItems)));
    }

    @Override
    public @NotNull Component getName(ItemStack itemStack) {
        DyeColor color = itemStack.get(DataComponents.BASE_COLOR);
        if(color != null) {
            String id = this.getDescriptionId();
            return Component.translatable(id + "." + color.name());
        } else {
            return super.getName(itemStack);
        }
    }
}
