package com.github.stellarwind22.shieldlib.fabric.init;

import com.github.stellarwind22.shieldlib.init.ShieldLibDeferredClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

@Environment(EnvType.CLIENT)
public class ShieldLibFabricDeferredClient implements Runnable {

    @Override
    public void run() {
        ShieldLibDeferredClient.init(FabricLoader.getInstance().isDevelopmentEnvironment());
    }
}
