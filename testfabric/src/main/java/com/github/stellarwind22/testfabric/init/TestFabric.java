package com.github.stellarwind22.testfabric.init;

import com.github.stellarwind22.testfabric.ShieldLibTests;
import net.fabricmc.api.ModInitializer;

public class TestFabric implements ModInitializer {

    public static final String MOD_ID = "testfabric";

    @Override
    public void onInitialize() {
        ShieldLibTests.init();
    }
}
