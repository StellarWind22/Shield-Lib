package com.github.stellarwind22.shieldlib.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ShieldLibClient {

    public static boolean IS_FABRIC;

    public static void init(boolean isFabric) {
        IS_FABRIC = isFabric;
    }
}
