package com.github.stellarwind22.shieldlib.lib.object;

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

public class ShieldLibItem extends Item {

    /**
     * @param properties        item properties.
     * @param cooldownSeconds   seconds shield will be in cooldown after disabled.
     * @param enchantability    enchantability of shield. Vanilla: 14
     * @param repairItems       item(s) for repairing shield.
     */
    @SuppressWarnings("deprecation")
    public ShieldLibItem(Properties properties, float cooldownSeconds, int enchantability, Item... repairItems) {
        this(properties, cooldownSeconds, enchantability,
                HolderSet.direct(
                        Arrays.stream(repairItems).map(Item::builtInRegistryHolder).collect(Collectors.toList())
                )
        );
    }

    /**
     * @param properties        item properties(INCLUDE YOUR OWN BlocksAttacks/ShieldInformation components!).
     * @param repairItems       item(s) for repairing shield.
     */
    @SuppressWarnings("deprecation")
    public ShieldLibItem(Properties properties, Item... repairItems) {
        this(properties,
                HolderSet.direct(
                        Arrays.stream(repairItems).map(Item::builtInRegistryHolder).collect(Collectors.toList())
                )
        );
    }

    /**
     * @param properties        item properties
     * @param cooldownSeconds   seconds shield will be disabled for after disabled.
     * @param material          tool material.
     */
    @SuppressWarnings("unused")
    public ShieldLibItem(Properties properties, float cooldownSeconds, ToolMaterial material) {
        this(
                properties,
                cooldownSeconds,
                material.enchantmentValue(),
                material.repairItems()
        );
    }

    /**
     * @param properties    item properties(INCLUDE YOUR OWN BlocksAttacks/ShieldInformation components!).
     * @param material      tool material.
     */
    @SuppressWarnings("unused")
    public ShieldLibItem(Properties properties, ToolMaterial material) {
        this(properties.enchantable(material.enchantmentValue()), material.repairItems());
    }

    /**
     * @param properties        item properties.
     * @param cooldownSeconds   seconds shield will be in cooldown after disabled.
     * @param enchantability    enchantability of shield. Vanilla: 14
     * @param repairItemTag     item tag for repairing shield.
     */
    public ShieldLibItem(Properties properties, float cooldownSeconds, int enchantability, TagKey<Item> repairItemTag) {
        this(
                properties,
                cooldownSeconds,
                enchantability,
                BuiltInRegistries
                        .acquireBootstrapRegistrationLookup(BuiltInRegistries.ITEM)
                        .getOrThrow(repairItemTag)
        );
    }

    /**
     * @param properties        item properties(INCLUDE YOUR OWN BlocksAttacks/ShieldInformation components!).
     * @param repairItemTag     item tag for repairing shield.
     */
    public ShieldLibItem(Properties properties, TagKey<Item> repairItemTag) {
        this(properties, BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.ITEM).getOrThrow(repairItemTag));
    }

    /**
     * @param properties        item properties.
     * @param cooldownSeconds   seconds shield will be in cooldown after disabled.
     * @param enchantability    enchantability of shield. Vanilla: 14
     * @param repairItems       list of items/tags for repairing shield.
     */
    public ShieldLibItem(Properties properties, float cooldownSeconds, int enchantability, @Nullable HolderSet<Item> repairItems) {
        super(attachRepairable(ShieldLibUtils.defaultShieldProperties(properties), repairItems)
                .enchantable(enchantability)
                .component(DataComponents.BLOCKS_ATTACKS,
                        ShieldLibUtils.withCooldownSeconds(
                                ShieldLibUtils.VANILLA_SHIELD_BLOCKS_ATTACKS_COMPONENT,
                                cooldownSeconds
                        )
                )
        );
    }

    /**
     * @param properties        item properties(INCLUDE YOUR OWN BlocksAttacks/ShieldInformation components!).
     * @param repairItems       list of items/tags for repairing shield.
     */
    public ShieldLibItem(Properties properties, @Nullable HolderSet<Item> repairItems) {
        super(attachRepairable(properties, repairItems));
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
