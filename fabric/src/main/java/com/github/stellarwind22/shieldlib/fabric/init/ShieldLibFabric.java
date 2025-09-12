package com.github.stellarwind22.shieldlib.fabric.init;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.lib.config.ShieldLibConfig;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public final class ShieldLibFabric implements ModInitializer {

    @Override
    public void onInitialize() {

        // Run our common setup.
        ShieldLib.init(FabricLoader.getInstance().isDevelopmentEnvironment());

        MidnightConfig.init(ShieldLib.MOD_ID, ShieldLibConfig.class);

        ShieldLib.LOGGER.info("ShieldLib initialized!");
    }
}
