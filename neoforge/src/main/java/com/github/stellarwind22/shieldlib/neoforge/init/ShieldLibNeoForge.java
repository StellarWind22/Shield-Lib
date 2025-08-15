package com.github.stellarwind22.shieldlib.neoforge.init;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.init.ShieldLibClient;
import com.github.stellarwind22.shieldlib.test.ShieldLibTests;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;

@Mod(ShieldLib.MOD_ID)
public final class ShieldLibNeoForge {

    public ShieldLibNeoForge() {
        // Run our common setup.
        ShieldLib.init(false);
        boolean isDev = !FMLEnvironment.production;

        if(isDev) {
            ShieldLibTests.initItems();
            ShieldLib.LOGGER.warn("TEST CODE IS CURRENTLY RUNNING!, IF YOU ARE NOT IN A DEV ENVIRONMENT THIS IS BAD!!");
        }

        if(FMLLoader.getDist() == Dist.CLIENT) {
            ShieldLibClient.init();

            if(isDev) {
                ShieldLibClientTests.init();
            }
        }

        ShieldLib.LOGGER.info("ShieldLib initialized!");
    }
}
