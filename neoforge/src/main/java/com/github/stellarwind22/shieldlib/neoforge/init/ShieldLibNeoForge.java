package com.github.stellarwind22.shieldlib.neoforge.init;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.init.ShieldLibClient;
import com.github.stellarwind22.shieldlib.lib.config.ShieldLibConfig;
import eu.midnightdust.lib.config.MidnightConfig;
import eu.midnightdust.neoforge.MidnightLibNeoForge;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;

@Mod(ShieldLib.MOD_ID)
public final class ShieldLibNeoForge {

    public ShieldLibNeoForge() {
        boolean isDev = !FMLEnvironment.production;

        // Run our common setup.
        ShieldLib.init(isDev);

        if(FMLLoader.getDist() == Dist.CLIENT) {
            ShieldLibClient.init(isDev);
        }

        ShieldLib.LOGGER.info("ShieldLib initialized!");
    }

    @EventBusSubscriber
    public static class Events {

        @SubscribeEvent
        public static void setup(FMLCommonSetupEvent event) {
            MidnightConfig.init(ShieldLib.MOD_ID, ShieldLibConfig.class);
        }
    }
}
