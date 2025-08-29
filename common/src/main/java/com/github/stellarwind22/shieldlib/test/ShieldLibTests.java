package com.github.stellarwind22.shieldlib.test;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.lib.event.ShieldBlockEvent;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibItem;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibUtils;
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
    protected static RegistrySupplier<Item> TARGE_SHIELD;
    protected static RegistrySupplier<Item> SPIKED_TOWER_SHIELD;
    protected static RegistrySupplier<Item> SPIKED_BUCKLER_SHIELD;
    protected static RegistrySupplier<Item> SPIKED_HEATER_SHIELD;
    protected static RegistrySupplier<Item> SPIKED_TARGE_SHIELD;

    protected static ResourceLocation REFLECT_ID = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "reflect");
    protected static ResourceLocation RECOVERY_ID = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "recovery");

    public static void init() {

        TEST_ITEMS = DeferredRegister.create(ShieldLib.MOD_ID, Registries.ITEM);

        SHIELD = registerItem("shield",
                props -> new ShieldLibItem(
                        props.durability(200),
                        5.0F,
                        9,
                        Items.OAK_PLANKS,
                        Items.SPRUCE_PLANKS));

        TOWER_SHIELD = registerItem("tower_shield",
                props -> new ShieldLibItem(
                        ShieldLibUtils.towerShieldProperties(props, 5.0F).durability(336),
                        Items.OAK_PLANKS
                )
        );

        COMPONENT_SHIELD = registerItem("component_shield",
                props -> new Item(
                        ShieldLibUtils.towerShieldProperties(props, 5.0F).durability(336)
                                .repairable(Items.OAK_PLANKS)
                                .enchantable(14)
                ));

        BUCKLER_SHIELD = registerItem("buckler_shield",
                props -> new ShieldLibItem(
                        ShieldLibUtils.bucklerShieldProperties(props, 2.5F).durability(269),
                        Items.OAK_PLANKS
                ));

        HEATER_SHIELD = registerItem("heater_shield",
                props -> new ShieldLibItem(
                        ShieldLibUtils.heaterShieldProperties(props, 3.5F).durability(302),
                        Items.OAK_PLANKS
                ));

        TARGE_SHIELD = registerItem("targe_shield",
                props -> new ShieldLibItem(
                        ShieldLibUtils.targeShieldProperties(props, 6.0F).durability(436),
                        Items.OAK_PLANKS
                ));

        SPIKED_TOWER_SHIELD = registerItem("spiked_tower_shield",
                props -> new ShieldLibItem(
                        ShieldLibUtils.spikedTowerShieldProperties(props, 5.0F).durability(336),
                        Items.OAK_PLANKS
                ));

        SPIKED_BUCKLER_SHIELD = registerItem("spiked_buckler_shield",
                props -> new ShieldLibItem(
                        ShieldLibUtils.spikedBucklerShieldProperties(props, 2.5F).durability(269),
                        Items.OAK_PLANKS
                ));

        SPIKED_HEATER_SHIELD = registerItem("spiked_heater_shield",
                props -> new ShieldLibItem(
                        ShieldLibUtils.spikedHeaterShieldProperties(props, 3.5F).durability(302),
                        Items.OAK_PLANKS
                ));

        SPIKED_TARGE_SHIELD = registerItem("spiked_targe_shield",
                props -> new ShieldLibItem(
                        ShieldLibUtils.spikedTargeShieldProperties(props, 6.0F).durability(436),
                        Items.OAK_PLANKS
                ));

        TEST_ITEMS.register();

        ShieldBlockEvent.EVENT.register((level, defender, source, amount, hand, itemStack) -> {

            ShieldLib.LOGGER.info("Shield Block Event Ran!");

            int enchantmentLevel = ShieldLibUtils.getEnchantmentLevel(REFLECT_ID, itemStack);

            if(enchantmentLevel > 0) {
                Entity attacker = source.getEntity();

                if(attacker == null) {
                    return;
                }

                if(defender instanceof Player) {
                    attacker.hurtServer(level, attacker.damageSources().playerAttack((Player) defender), amount * (0.25F * enchantmentLevel));
                } else {
                    attacker.hurtServer(level, attacker.damageSources().mobAttack(defender), amount * (0.25F * enchantmentLevel));
                }
            }
        });

        ShieldLib.registerCooldownModifier(((player, shield, blocksAttacks, currentCooldown) -> {
            int enchantmentLevel = ShieldLibUtils.getEnchantmentLevel(RECOVERY_ID, shield);

            if(enchantmentLevel > 0) {
                return Math.round((currentCooldown - (currentCooldown * (0.20F * enchantmentLevel))) * 100.F) / 100.0F;
            }

            return currentCooldown;
        }));
    }

    public static void initClient() {

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
