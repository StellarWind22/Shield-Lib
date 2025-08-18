package com.github.stellarwind22.shieldlib.fabric.init;

import com.github.stellarwind22.shieldlib.init.ShieldLibClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

@Environment(EnvType.CLIENT)
public final class ShieldLibFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ShieldLibClient.init(FabricLoader.getInstance().isDevelopmentEnvironment());
    }
}
