package com.github.stellarwind22.shieldlib.fabric.init;

import com.github.stellarwind22.shieldlib.init.ShieldLibEnchantment;
import net.fabricmc.loader.api.FabricLoader;

public class ShieldLibFabricEnchantment implements Runnable {

    @Override
    public void run() {
        ShieldLibEnchantment.init(FabricLoader.getInstance().isDevelopmentEnvironment());
    }
}
