package com.github.stellarwind22.shieldlib.init;

import com.github.stellarwind22.shieldlib.lib.model.BucklerShieldLibModel;
import com.github.stellarwind22.shieldlib.lib.render.BucklerShieldModelRenderer;
import com.github.stellarwind22.shieldlib.lib.render.VanillaShieldModelRenderer;
import com.github.stellarwind22.shieldlib.mixin.SpecialModelRenderersAccessor;
import com.mojang.serialization.MapCodec;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MaterialMapper;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class ShieldLibClient {

    public static final ResourceLocation BANNER_SHIELD_MODEL_TYPE = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "banner_shield");
    public static final ResourceLocation BUCKLER_SHIELD_MODEL_TYPE = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "buckler_shield");

    public static final ResourceLocation SHAPED_BANNER = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "textures/atlas/shaped_banners.png");
    public static TextureAtlas SHIELD_ATLAS;
    public static final MaterialMapper SHAPED_BANNER_MAPPER = new MaterialMapper(SHAPED_BANNER, "entity/shaped_banner");
    public static final Map<ResourceLocation, Material> SHAPED_BANNER_MATERIALS = new HashMap<>();

    public static final ExtraCodecs.LateBoundIdMapper<ResourceLocation, MapCodec<? extends SpecialModelRenderer.Unbaked>> ID_MAPPER = SpecialModelRenderersAccessor.getIDMapper();

    public static void init() {

        SHIELD_ATLAS = new TextureAtlas(SHAPED_BANNER);
        Minecraft.getInstance().getTextureManager().register(SHAPED_BANNER, SHIELD_ATLAS);

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

    public static Material getShapedBannerMaterial(ResourceLocation shape, Holder<BannerPattern> holder) {
        ResourceLocation assetId = holder.value().assetId();

        if(assetId.toString().equals("shieldlib:vanilla")) {
            return Sheets.getShieldMaterial(holder);
        }

        ResourceLocation key = shape.withPrefix("/" + assetId.getPath());
        return SHAPED_BANNER_MATERIALS.computeIfAbsent(key, SHAPED_BANNER_MAPPER::apply);
    }
}
