package com.github.stellarwind22.testfabric.init;

import com.github.stellarwind22.testfabric.ShieldLibTests;
import net.fabricmc.api.ClientModInitializer;

public class TestFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ShieldLibTests.initClient();
    }
}
