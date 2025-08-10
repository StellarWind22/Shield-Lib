package com.github.stellarwind22.shieldlib.fabric.init;

import com.github.stellarwind22.shieldlib.init.ShieldLibClient;
import net.fabricmc.api.ClientModInitializer;

public final class ShieldLibFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ShieldLibClient.init(true);
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
    }
}
