package com.github.stellarwind22.shieldlib.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;

@Environment(EnvType.CLIENT)
public class ShieldLibDeferredClient {

    public static TextureAtlas SHIELD_ATLAS;

    public static void init(boolean isDev) {
        SHIELD_ATLAS = new TextureAtlas(ShieldLibClient.SHAPED_BANNER);
        Minecraft.getInstance().getTextureManager().register(ShieldLibClient.SHAPED_BANNER, SHIELD_ATLAS);
    }
}
