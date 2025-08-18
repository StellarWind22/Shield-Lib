package com.github.stellarwind22.shieldlib.fabric.init;

import com.github.stellarwind22.shieldlib.fabric.config.ShieldLibConfigFabric;
import com.github.stellarwind22.shieldlib.init.ShieldLib;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

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

        ShieldLib.LOGGER.info("ShieldLib initialized!");
    }
}
