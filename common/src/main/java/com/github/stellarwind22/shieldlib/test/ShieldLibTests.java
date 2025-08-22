package com.github.stellarwind22.shieldlib.test;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.lib.event.ShieldBlockEvent;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibItem;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibUtils;
import dev.architectury.event.EventResult;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.function.Function;

public class ShieldLibTests {

    protected static DeferredRegister<Item> TEST_ITEMS;

    protected static RegistrySupplier<Item> SHIELD;
    protected static RegistrySupplier<Item> VANILLA_SHIELD;
    protected static RegistrySupplier<Item> COMPONENT_SHIELD;
    protected static RegistrySupplier<Item> BUCKLER_SHIELD;

    protected static ResourceLocation REFLECT_ID = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "reflect");
    protected static ResourceLocation RECOVERY_ID = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "recovery");

    public static void init() {

        TEST_ITEMS = DeferredRegister.create(ShieldLib.MOD_ID, Registries.ITEM);

        SHIELD = registerItem("shield",
                props -> new ShieldLibItem(
                        props.durability(200),
                        100,
                        9,
                        Items.OAK_PLANKS,
                        Items.SPRUCE_PLANKS));

        VANILLA_SHIELD = registerItem("vanilla_shield",
                props -> new ShieldLibItem(
                        ShieldLibUtils.vanillaShieldProperties(props),
                        ShieldLibUtils.VANILLA_SHIELD_COOLDOWN_TICKS,
                        ShieldLibUtils.VANILLA_SHIELD_ENCHANTABILITY,
                        Items.OAK_PLANKS
                )
        );

        COMPONENT_SHIELD = registerItem("component_shield",
                props -> new Item(
                        ShieldLibUtils.vanillaShieldProperties(props)
                                .repairable(Items.OAK_PLANKS)
                                .enchantable(ShieldLibUtils.VANILLA_SHIELD_ENCHANTABILITY)
                ));

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

        ShieldLib.registerCooldownModifier(((player, shield, blocksAttacks, currentCooldown) -> {
            int enchantmentLevel = ShieldLibUtils.getEnchantmentLevel(RECOVERY_ID, shield);

            if(enchantmentLevel > 0) {
                return currentCooldown - (currentCooldown * (0.20F * enchantmentLevel));
            }

            return  currentCooldown;
        }));

        ShieldLib.registerMovementModifier(((player, stack, blocksAttacks, movement) -> {
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
            //Undo slowdown from blocking if buckler
            if(id.equals(ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "buckler_shield"))) {
                return movement.scale(5.0F);
            }
            return movement;
        }));
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
