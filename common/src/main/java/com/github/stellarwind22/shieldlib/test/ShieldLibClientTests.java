package com.github.stellarwind22.shieldlib.test;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.init.ShieldLibClient;
import com.github.stellarwind22.shieldlib.lib.render.ShieldLibModelRenderer;
import net.minecraft.resources.ResourceLocation;

public class ShieldLibClientTests {

    protected static ResourceLocation BUCKLER_SHIELD_MODEL_TYPE = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "buckler_shield");

    public static void init() {

        ShieldLibClient.ID_MAPPER.put(
                BUCKLER_SHIELD_MODEL_TYPE,
                ShieldLibModelRenderer.Unbaked.CODEC
        );
    }
}
