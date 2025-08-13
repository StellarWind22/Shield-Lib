package com.github.stellarwind22.shieldlib.init;

import com.github.stellarwind22.shieldlib.lib.object.ShieldLibTags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ShieldLib {

    public static final String MOD_ID = "shieldlib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static boolean IS_FABRIC;

    public static void init(boolean isFabric) {

        IS_FABRIC = isFabric;

        // Write common init code here.
        ShieldLibTags.init();
    }
}
