package com.github.stellarwind22.shieldlib.init;

import com.github.stellarwind22.shieldlib.lib.object.ShieldLibTags;
import com.github.stellarwind22.shieldlib.test.ShieldLibTests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ShieldLib {

    public static final String MOD_ID = "shieldlib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init(boolean isDev) {

        // Write common init code here.
        ShieldLibTags.init();

        if(isDev) {
            ShieldLibTests.initItems();
            ShieldLib.LOGGER.warn("TEST CODE IS CURRENTLY RUNNING!, IF YOU ARE NOT IN A DEV ENVIRONMENT THIS IS BAD!!");
        }
    }
}
