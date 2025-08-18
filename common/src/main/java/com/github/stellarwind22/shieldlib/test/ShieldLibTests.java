package com.github.stellarwind22.shieldlib.test;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.lib.event.ShieldBlockEvent;
import com.github.stellarwind22.shieldlib.lib.event.ShieldDisableEvent;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibItem;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibUtils;
import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.EventResult;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.function.Function;

public class ShieldLibTests {

    protected static DeferredRegister<Item> TEST_ITEMS;
    protected static DeferredRegister<Enchantment> TEST_ENCHANTMENTS;

    protected static RegistrySupplier<Item> SHIELD;
    protected static RegistrySupplier<Item> BANNER_SHIELD;
    protected static RegistrySupplier<Item> BUCKLER_SHIELD;

    protected static RegistrySupplier<Enchantment> REFLECT;
    protected static ResourceLocation REFLECT_ID = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "reflect");
    protected static RegistrySupplier<Enchantment> RECOVERY;
    protected static ResourceLocation RECOVERY_ID = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "recovery");

    public static void initItems() {

        TEST_ITEMS = DeferredRegister.create(ShieldLib.MOD_ID, Registries.ITEM);

        SHIELD = registerItem("shield",
                props -> new ShieldLibItem(
                        props.durability(200),
                        100,
                        9,
                        Items.OAK_PLANKS,
                        Items.SPRUCE_PLANKS));

        BANNER_SHIELD = registerItem("banner_shield",
                props -> new ShieldLibItem(
                        ShieldLibUtils.vanillaShieldProperties(props),
                        ShieldLibUtils.VANILLA_SHIELD_COOLDOWN_TICKS,
                        ShieldLibUtils.VANILLA_SHIELD_ENCHANTABILITY,
                        Items.OAK_PLANKS
                )
        );

        BUCKLER_SHIELD = registerItem("buckler_shield",
                props -> new ShieldLibItem(
                        ShieldLibUtils.vanillaShieldProperties(props),
                        ShieldLibUtils.VANILLA_SHIELD_COOLDOWN_TICKS,
                        ShieldLibUtils.VANILLA_SHIELD_ENCHANTABILITY,
                        Items.OAK_PLANKS
                ));

        TEST_ITEMS.register();

        ShieldBlockEvent.EVENT.register((level, defender, source, amount, hand, itemStack) -> {

            ShieldLib.LOGGER.info("Shield Block Event Ran!");

            int enchantmentLevel = ShieldLibUtils.getEnchantmentLevel(REFLECT_ID, itemStack);

            if(enchantmentLevel > 0) {
                Entity attacker = source.getEntity();

                if(attacker == null) {
                    return EventResult.pass();
                }

                if(defender instanceof Player) {
                    attacker.hurtServer(level, attacker.damageSources().playerAttack((Player) defender), amount * (0.25F * enchantmentLevel));
                } else {
                    attacker.hurtServer(level, attacker.damageSources().mobAttack(defender), amount * (0.25F * enchantmentLevel));
                }
            }
            return EventResult.pass();
        });

        ShieldDisableEvent.EVENT.register((level, attacker, defender, isPlayer, hand, itemStack, disableForSeconds) -> {

            ShieldLib.LOGGER.info("Shield Pre Disable Event Ran!");

            if(isPlayer) {
                int enchantmentLevel = ShieldLibUtils.getEnchantmentLevel(RECOVERY_ID, itemStack);

                if(enchantmentLevel > 0) {
                    float result = disableForSeconds - (disableForSeconds * (0.25F * enchantmentLevel));
                    ShieldLib.LOGGER.info("Disabling for {} seconds instead of {}!", result, disableForSeconds);
                    return CompoundEventResult.interruptDefault(result);
                }
            }

            return CompoundEventResult.pass();
        });
    }

    public static void initEnchantments(HolderSet<Item> holders) {

        TEST_ENCHANTMENTS = DeferredRegister.create(ShieldLib.MOD_ID, Registries.ENCHANTMENT);

        REFLECT = TEST_ENCHANTMENTS.register("reflect", () -> Enchantment.enchantment(
                Enchantment.definition(
                        holders,
                        10,
                        3,
                        Enchantment.dynamicCost(1, 10),
                        Enchantment.dynamicCost(1, 15),
                        5,
                        EquipmentSlotGroup.HAND
                )

        ).build(REFLECT_ID));

        RECOVERY = TEST_ENCHANTMENTS.register("recovery", () -> Enchantment.enchantment(
                Enchantment.definition(
                        holders,
                        10,
                        3,
                        Enchantment.dynamicCost(1, 10),
                        Enchantment.dynamicCost(1, 15),
                        5,
                        EquipmentSlotGroup.HAND
                )

        ).build(RECOVERY_ID));

        TEST_ENCHANTMENTS.register();
    }

    private static <T extends Item> RegistrySupplier<T> registerItem(String name, Function<Item.Properties, T> constructor) {
        return TEST_ITEMS.register(name, () -> {
            ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, name));
            Item.Properties properties = new Item.Properties();
            properties = properties.setId(key);
            return constructor.apply(properties);
        });
    }
}
