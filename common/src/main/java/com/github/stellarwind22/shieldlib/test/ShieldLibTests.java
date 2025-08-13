package com.github.stellarwind22.shieldlib.test;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.lib.event.ShieldBlockEvent;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibItem;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibTags;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibUtils;
import dev.architectury.event.EventResult;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ShieldLibTests {

    protected static DeferredRegister<Item> TEST_ITEMS;
    protected static DeferredRegister<Enchantment> TEST_ENCHANTMENTS;

    protected static RegistrySupplier<Item> SHIELD;

    protected static RegistrySupplier<Item> BANNER_SHIELD;

    protected static RegistrySupplier<Enchantment> REFLECT;
    protected static RegistrySupplier<Enchantment> RECOVERY;

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

        TEST_ITEMS.register();
    }

    public static void initEnchantments() {

        TEST_ENCHANTMENTS = DeferredRegister.create(ShieldLib.MOD_ID, Registries.ENCHANTMENT);

        Iterable<Holder<Item>> holders = BuiltInRegistries.ITEM.getTagOrEmpty(ShieldLibTags.SHIELD_ENCHANTABLE);
        List<Holder<Item>> holderList = new ArrayList<>();
        holders.forEach(holderList::add);
        HolderSet<Item> holderSet = HolderSet.direct(holderList);
        ResourceLocation reflectId = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "reflect");

        REFLECT = TEST_ENCHANTMENTS.register("reflect", () -> Enchantment.enchantment(
                Enchantment.definition(
                        holderSet,
                        10,
                        3,
                        Enchantment.dynamicCost(1, 10),
                        Enchantment.dynamicCost(1, 15),
                        5,
                        EquipmentSlotGroup.HAND
                )

        ).build(reflectId));

        TEST_ENCHANTMENTS.register();

        ShieldBlockEvent.EVENT.register((defender, source, amount, hand, itemStack) -> {
            if(ShieldLibUtils.isShieldItem(itemStack)) {
                int level = ShieldLibUtils.getEnchantmentLevel(reflectId, itemStack);

                if(level > 0) {
                    Entity attacker = source.getEntity();

                    if(attacker != null) {
                        return EventResult.pass();
                    }
                }
            }
            return EventResult.pass();
        });
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
