package com.github.stellarwind22.shieldlib.fabric.init;

import com.github.stellarwind22.shieldlib.fabric.config.ShieldLibConfigFabric;
import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.lib.object.ShieldEnchReg;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibTags;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public final class ShieldLibFabric implements ModInitializer {

    @Override
    public void onInitialize() {

        boolean isDev = FabricLoader.getInstance().isDevelopmentEnvironment();
        // Run our common setup.
        ShieldLib.init(isDev,
                () -> ShieldLibConfigFabric.enable_tooltips,
                () -> ShieldLibConfigFabric.advanced_tooltips,
                () -> ShieldLibConfigFabric.vanilla_shield_enchantability);

        MidnightConfig.init(ShieldLib.MOD_ID, ShieldLibConfigFabric.class);

        FabricLoader.getInstance().getEntrypointContainers(ShieldLib.MOD_ID + "enchantment", ShieldEnchReg.class)
                .forEach(container -> {
                    Iterable<Holder<Item>> holders = BuiltInRegistries.ITEM.getTagOrEmpty(ShieldLibTags.SHIELD_ENCHANTABLE);
                    List<Holder<Item>> holderList = new ArrayList<>();
                    holders.forEach(holderList::add);
                    HolderSet<Item> tag = HolderSet.direct(holderList);
                    container.getEntrypoint().accept(tag);
                });

        ShieldLib.LOGGER.info("ShieldLib initialized!");
    }
}
