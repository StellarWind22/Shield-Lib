package com.github.stellarwind22.shieldlib.neoforge.init;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.init.ShieldLibClient;
import com.github.stellarwind22.shieldlib.neoforge.config.ShieldLibConfigNeoForge;
import eu.midnightdust.lib.config.MidnightConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;

@Mod(ShieldLib.MOD_ID)
public final class ShieldLibNeoForge {

    public ShieldLibNeoForge() {
        // Run our common setup.
        boolean isDev = !FMLEnvironment.production;
        ShieldLib.init(isDev,
                () -> ShieldLibConfigNeoForge.enable_tooltips,
                () -> ShieldLibConfigNeoForge.advanced_tooltips,
                () -> ShieldLibConfigNeoForge.vanilla_shield_enchantability);

        MidnightConfig.init(ShieldLib.MOD_ID, ShieldLibConfigNeoForge.class);

        if(FMLLoader.getDist() == Dist.CLIENT) {
            ShieldLibClient.init(isDev);
        }

        ShieldLib.LOGGER.info("ShieldLib initialized!");
    }
}
