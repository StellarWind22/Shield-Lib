package com.github.stellarwind22.shieldlib.test;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibItem;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibTags;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibUtils;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.function.Function;

public class ShieldLibTests {

    protected static DeferredRegister<Item> TEST_ITEMS;
    protected static DeferredRegister<Enchantment> TEST_ENCHANTMENTS;

    protected static RegistrySupplier<Item> SHIELD;

    protected static RegistrySupplier<Item> BANNER_SHIELD;

    protected static RegistrySupplier<Enchantment> REFLECT_ENCHANTMENT;

    public static void init() {

        TEST_ITEMS = DeferredRegister.create(ShieldLib.MOD_ID, Registries.ITEM);
        TEST_ENCHANTMENTS = DeferredRegister.create(ShieldLib.MOD_ID, Registries.ENCHANTMENT);

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

        REFLECT_ENCHANTMENT = registerEnchantment("reflect", Enchantment.enchantment(
                Enchantment.definition(
                        BuiltInRegistries
                                .acquireBootstrapRegistrationLookup(BuiltInRegistries.ITEM)
                                .getOrThrow(ShieldLibTags.SHIELD_ENCHANTABLE),
                        10,
                        3,
                        Enchantment.dynamicCost(1, 10),
                        Enchantment.dynamicCost(1, 15),
                        5,
                        EquipmentSlotGroup.HAND
                )

        ).build(ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "reflect")));

        TEST_ITEMS.register();
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

    private static <T extends Enchantment> RegistrySupplier<T> registerEnchantment(String name, T enchantment) {
        return TEST_ENCHANTMENTS.register(name, () -> enchantment);
    }
}
