package com.github.stellarwind22.shieldlib.test;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.lib.config.ShieldLibConfig;
import com.github.stellarwind22.shieldlib.lib.event.ShieldBlockEvent;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibItem;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibUtils;
import dev.architectury.event.EventResult;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
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
    protected static RegistrySupplier<Item> TOWER_SHIELD;
    protected static RegistrySupplier<Item> COMPONENT_SHIELD;
    protected static RegistrySupplier<Item> BUCKLER_SHIELD;
    protected static RegistrySupplier<Item> HEATER_SHIELD;
    protected static RegistrySupplier<Item> SPIKED_TOWER_SHIELD;
    protected static RegistrySupplier<Item> SPIKED_BUCKLER_SHIELD;
    protected static RegistrySupplier<Item> SPIKED_HEATER_SHIELD;

    protected static ResourceLocation REFLECT_ID = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "reflect");
    protected static ResourceLocation RECOVERY_ID = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "recovery");

    public static void init() {

        TEST_ITEMS = DeferredRegister.create(ShieldLib.MOD_ID, Registries.ITEM);

        SHIELD = registerItem("shield",
                props -> new ShieldLibItem(
                        props.durability(200),
                        ShieldLibConfig.vanilla_shield_cooldown_ticks,
                        9,
                        Items.OAK_PLANKS,
                        Items.SPRUCE_PLANKS));

        TOWER_SHIELD = registerItem("tower_shield",
                props -> new ShieldLibItem(
                        ShieldLibUtils.towerShieldProperties(props, ShieldLibConfig.vanilla_shield_cooldown_ticks).durability(336),
                        Items.OAK_PLANKS
                )
        );

        COMPONENT_SHIELD = registerItem("component_shield",
                props -> new Item(
                        ShieldLibUtils.towerShieldProperties(props, ShieldLibConfig.vanilla_shield_cooldown_ticks)
                                .repairable(Items.OAK_PLANKS)
                                .enchantable(14)
                ));

        BUCKLER_SHIELD = registerItem("buckler_shield",
                props -> new ShieldLibItem(
                        ShieldLibUtils.bucklerShieldProperties(props, 50).durability(269),
                        Items.OAK_PLANKS
                ));

        HEATER_SHIELD = registerItem("heater_shield",
                props -> new ShieldLibItem(
                        ShieldLibUtils.heaterShieldProperties(props, 70).durability(302),
                        Items.OAK_PLANKS
                ));

        SPIKED_TOWER_SHIELD = registerItem("spiked_tower_shield",
                props -> new ShieldLibItem(
                        ShieldLibUtils.spikedTowerShieldProperties(props, ShieldLibConfig.vanilla_shield_cooldown_ticks).durability(336),
                        Items.OAK_PLANKS
                ));

        SPIKED_BUCKLER_SHIELD = registerItem("spiked_buckler_shield",
                props -> new ShieldLibItem(
                        ShieldLibUtils.spikedBucklerShieldProperties(props, 50).durability(269),
                        Items.OAK_PLANKS
                ));

        SPIKED_HEATER_SHIELD = registerItem("spiked_heater_shield",
                props -> new ShieldLibItem(
                        ShieldLibUtils.spikedHeaterShieldProperties(props, 70).durability(302),
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
                return Math.round((currentCooldown - (currentCooldown * (0.20F * enchantmentLevel))) * 100.F) / 100.0F;
            }

            return currentCooldown;
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
