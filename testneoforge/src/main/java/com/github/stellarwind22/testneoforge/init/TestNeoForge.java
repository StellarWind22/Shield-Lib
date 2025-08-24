package com.github.stellarwind22.testneoforge.init;

import com.github.stellarwind22.testneoforge.ShieldLibTests;

@Mod("testneoforge")
public final class TestNeoForge {

    public static final String MOD_ID = "testneoforge";

    public TestNeoForge() {
        ShieldLibTests.init();

        if(FMLLoader.getDist() == Dist.CLIENT) {
            ShieldLibTests.initClient();
        }
    }
}
