package com.github.stellarwind22.shieldlib.test;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.mojang.datafixers.types.Func;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ShieldLibTests {

    //protected final static ShieldLibItem SHIELD = registerItem("shield",
    //        (props) -> new ShieldLibItem(props.durability(200), 100, 9, Items.OAK_PLANKS, Items.SPRUCE_PLANKS));



    private static <T extends Item> T registerItem(String name, Func<Item.Properties, T> constructor) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, name));
        Item.Properties properties = new Item.Properties();
        return null;
    }
}
