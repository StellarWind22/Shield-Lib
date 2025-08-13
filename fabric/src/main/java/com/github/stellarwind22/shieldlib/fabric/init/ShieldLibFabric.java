package com.github.stellarwind22.shieldlib.fabric.init;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.test.ShieldLibTests;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public final class ShieldLibFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        // Run our common setup.
        ShieldLib.init(true);

        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            ShieldLibTests.initItems();
            ShieldLib.LOGGER.warn("TEST CODE IS CURRENTLY RUNNING!, IF YOU ARE NOT IN A DEV ENVIRONMENT THIS IS BAD!!");

            //Schedule stuff to run after tags are bound
            FabricLoader.getInstance().getEntrypointContainers(ShieldLib.MOD_ID + "deferred", Runnable.class)
                    .forEach(container -> { container.getEntrypoint().run();});
        }

        ShieldLib.LOGGER.info("ShieldLib initialized!");
    }
}
