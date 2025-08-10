package com.github.stellarwind22.shieldlib.test;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibItem;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibUtils;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.function.Function;
import java.util.function.Supplier;

public class ShieldLibTests {

    protected static final DeferredRegister<Item> TEST_ITEMS = DeferredRegister.create(ShieldLib.MOD_ID, Registries.ITEM);

    protected final static RegistrySupplier<Item> SHIELD = registerItem("shield",
            props -> new ShieldLibItem(props.durability(200), 100, 9, Items.OAK_PLANKS, Items.SPRUCE_PLANKS));

    protected final static RegistrySupplier<Item> BANNER_SHIELD = registerItem("banner_shield",
            props -> new ShieldLibItem(ShieldLibUtils.vanillaShieldProperties(props),
                    ShieldLibUtils.VANILLA_SHIELD_COOLDOWN_TICKS,
                    ShieldLibUtils.VANILLA_SHIELD_ENCHANTABILITY,
                    Items.OAK_PLANKS));

    public static void init() {
        TEST_ITEMS.register();
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
