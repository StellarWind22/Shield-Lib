package com.github.stellarwind22.shieldlib.init;

import com.github.stellarwind22.shieldlib.lib.object.ShieldLibTags;
import com.github.stellarwind22.shieldlib.test.ShieldLibTests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public final class ShieldLib {

    public static final String MOD_ID = "shieldlib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Supplier<Boolean> enableTooltips;
    public static Supplier<Boolean> advancedTooltips;
    public static Supplier<Integer> vanillaShieldEnchantability;

    public static void init(boolean isDev,
                            Supplier<Boolean> enableTooltips,
                            Supplier<Boolean> advancedTooltips,
                            Supplier<Integer> vanillaShieldEnchantability) {

        ShieldLib.enableTooltips = enableTooltips;
        ShieldLib.advancedTooltips = advancedTooltips;
        ShieldLib.vanillaShieldEnchantability = vanillaShieldEnchantability;

        // Write common init code here.
        ShieldLibTags.init();

        if(isDev) {
            ShieldLibTests.initItems();
            ShieldLibEnchantment.register(ShieldLibTests::initEnchantments);
            ShieldLib.LOGGER.warn("TEST CODE IS CURRENTLY RUNNING!, IF YOU ARE NOT IN A DEV ENVIRONMENT THIS IS BAD!!");
        }
    }
}
