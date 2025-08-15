package com.github.stellarwind22.shieldlib.init;

import com.github.stellarwind22.shieldlib.lib.model.BucklerShieldLibModel;
import com.github.stellarwind22.shieldlib.lib.model.ShieldModel;
import com.github.stellarwind22.shieldlib.lib.render.BucklerShieldModelRenderer;
import com.github.stellarwind22.shieldlib.lib.render.VanillaShieldModelRenderer;
import com.github.stellarwind22.shieldlib.mixin.SpecialModelRenderersAccessor;
import com.mojang.serialization.MapCodec;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;

@Environment(EnvType.CLIENT)
public class ShieldLibClient {

    public static final ResourceLocation BANNER_SHIELD_MODEL_TYPE = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "banner_shield");
    public static final ResourceLocation BUCKLER_SHIELD_MODEL_TYPE = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "buckler_shield");

    public static final ExtraCodecs.LateBoundIdMapper<ResourceLocation, MapCodec<? extends SpecialModelRenderer.Unbaked>> ID_MAPPER = SpecialModelRenderersAccessor.getIDMapper();

    public static void init() {

        ID_MAPPER.put(
                BANNER_SHIELD_MODEL_TYPE,
                VanillaShieldModelRenderer.Unbaked.CODEC
        );

        ID_MAPPER.put(
                BUCKLER_SHIELD_MODEL_TYPE,
                BucklerShieldModelRenderer.Unbaked.CODEC
        );

        EntityModelLayerRegistry.register(BucklerShieldLibModel.LOCATION, BucklerShieldLibModel::createLayer);
    }
}
