package com.github.stellarwind22.shieldlib.test;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibItem;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class ShieldLibTests {

    protected static ShieldLibItem SHIELD;
    protected static ShieldLibItem BANNER_SHIELD;

    protected static final DeferredRegister<Item> TEST_ITEMS = DeferredRegister.create(ShieldLib.MOD_ID, Registries.ITEM);

    public static void init() {

    }



    private static <T extends Item> T registerItem(String name, Function<Item.Properties, T> constructor) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, name));
        Item.Properties properties = new Item.Properties();
        properties = properties.setId(key);
        T item = constructor.apply(properties);
        return null;
    }
}
