package com.github.stellarwind22.shieldlib.fabric.init;

import com.github.stellarwind22.shieldlib.init.ShieldLibDeferred;
import net.fabricmc.loader.api.FabricLoader;

public class ShieldLibFabricDeferred implements Runnable {

    @Override
    public void run() {
        ShieldLibDeferred.init(FabricLoader.getInstance().isDevelopmentEnvironment());
    }
}
