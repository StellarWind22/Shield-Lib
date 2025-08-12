package com.github.stellarwind22.shieldlib.init;

import com.github.stellarwind22.shieldlib.lib.render.ShieldLibModelRenderer;
import com.github.stellarwind22.shieldlib.mixin.SpecialModelRenderersAccessor;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;

@Environment(EnvType.CLIENT)
public class ShieldLibClient {

    public static final ResourceLocation BANNER_SHIELD_MODEL_TYPE = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "banner_shield");

    public static void init() {

        ExtraCodecs.LateBoundIdMapper<ResourceLocation, MapCodec<? extends SpecialModelRenderer.Unbaked>> idMapper = SpecialModelRenderersAccessor.getIDMapper();

        idMapper.put(
                BANNER_SHIELD_MODEL_TYPE,
                ShieldLibModelRenderer.Unbaked.CODEC
        );
    }
}
