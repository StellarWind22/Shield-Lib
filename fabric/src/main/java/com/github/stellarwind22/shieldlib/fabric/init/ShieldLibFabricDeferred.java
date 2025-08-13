package com.github.stellarwind22.shieldlib.fabric.init;

import com.github.stellarwind22.shieldlib.test.ShieldLibTests;

public class ShieldLibFabricDeferred implements Runnable {

    @Override
    public void run() {
        ShieldLibTests.initEnchantments();
    }
}
