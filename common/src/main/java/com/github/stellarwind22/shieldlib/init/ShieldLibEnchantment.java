package com.github.stellarwind22.shieldlib.init;

import com.github.stellarwind22.shieldlib.test.ShieldLibTests;

public class ShieldLibEnchantment {

    public static void init(boolean isDev) {

        if(isDev) {
            ShieldLibTests.initEnchantments();
        }
    }
}
