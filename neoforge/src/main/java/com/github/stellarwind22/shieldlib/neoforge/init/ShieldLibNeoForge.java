package com.github.stellarwind22.shieldlib.neoforge.init;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.test.ShieldLibTests;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(ShieldLib.MOD_ID)
public final class ShieldLibNeoForge {

    public ShieldLibNeoForge() {
        // Run our common setup.
        ShieldLib.init(false);

        if(!FMLEnvironment.production) {
            ShieldLibTests.init();
            ShieldLib.LOGGER.warn("TEST CODE IS CURRENTLY RUNNING!, IF YOU ARE NOT IN A DEV ENVIRONMENT THIS IS BAD!!");
        }

        ShieldLib.LOGGER.info("ShieldLib initialized!");
    }
}
