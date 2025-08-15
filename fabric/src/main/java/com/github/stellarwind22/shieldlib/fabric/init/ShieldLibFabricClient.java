package com.github.stellarwind22.shieldlib.fabric.init;

import com.github.stellarwind22.shieldlib.init.ShieldLibClient;
import com.github.stellarwind22.shieldlib.test.ShieldLibClientTests;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public final class ShieldLibFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ShieldLibClient.init();

        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            ShieldLibClientTests.init();
        }
    }
}
