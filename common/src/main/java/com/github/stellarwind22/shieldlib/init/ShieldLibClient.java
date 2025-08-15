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
import net.minecraft.client.renderer.MaterialMapper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.entity.BannerPattern;

@Environment(EnvType.CLIENT)
public class ShieldLibClient {

    public static final ResourceLocation BANNER_SHIELD_MODEL_TYPE = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "banner_shield");
    public static final ResourceLocation BUCKLER_SHIELD_MODEL_TYPE = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "buckler_shield");

    public static final ResourceLocation SHAPED_BANNER = ResourceLocation.withDefaultNamespace("textures/atlas/shaped_banners.png");
    public static final RenderType SHAPED_BANNER_TYPE = RenderType.entityNoOutline(SHAPED_BANNER);
    public static final MaterialMapper SHAPED_BANNER_MAPPER = new MaterialMapper(ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "entity/shaped_banner"), "entity/shaped_banner");

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

    public static Material getShapedBannerMaterial(ShieldModel.Shape shape, Holder<BannerPattern> holder) {
        return null;
    }
}
