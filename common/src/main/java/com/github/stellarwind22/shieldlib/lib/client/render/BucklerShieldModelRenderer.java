package com.github.stellarwind22.shieldlib.lib.client.render;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.lib.client.model.BucklerShieldLibModel;
import com.github.stellarwind22.shieldlib.lib.client.model.ShieldModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class BucklerShieldModelRenderer implements ShieldModelRenderer {

    private final ResourceLocation baseModel, baseModelNoPat;
    private final BucklerShieldLibModel model;

    public static final ModelLayerLocation BUCKLER_MODEL_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "buckler_shield"), "main");

    public BucklerShieldModelRenderer(ResourceLocation baseModel, ResourceLocation baseModelNoPat, BucklerShieldLibModel model) {
        this.baseModel = baseModel;
        this.baseModelNoPat = baseModelNoPat;
        this.model = model;
    }

    @Override
    public ResourceLocation baseModel() {
        return this.baseModel;
    }

    @Override
    public ResourceLocation baseModelNoPat() {
        return this.baseModelNoPat;
    }

    @Override
    public ShieldModel model() {
        return this.model;
    }

    @Override
    public void submit(@Nullable DataComponentMap dataComponentMap, ItemDisplayContext itemDisplayContext, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, int j, boolean bl, int k) {
        BannerPatternLayers bannerPatternLayers = dataComponentMap != null ? (BannerPatternLayers)dataComponentMap.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY) : BannerPatternLayers.EMPTY;
        DyeColor dyeColor = dataComponentMap != null ? (DyeColor)dataComponentMap.get(DataComponents.BASE_COLOR) : null;
        boolean bl2 = !bannerPatternLayers.layers().isEmpty() || dyeColor != null;
        poseStack.pushPose();
        poseStack.scale(1.0F, -1.0F, -1.0F);
        Material material = bl2 ? ModelBakery.SHIELD_BASE : ModelBakery.NO_PATTERN_SHIELD;
        submitNodeCollector.submitModelPart(this.model.handle(), poseStack, this.model.renderType(material.atlasLocation()), i, j, this.materials.get(material), false, false, -1, (ModelFeatureRenderer.CrumblingOverlay)null, k);
        if (bl2) {
            BannerRenderer.submitPatterns(this.materials, poseStack, submitNodeCollector, i, j, this.model, Unit.INSTANCE, material, false, (DyeColor) Objects.requireNonNullElse(dyeColor, DyeColor.WHITE), bannerPatternLayers, bl, (ModelFeatureRenderer.CrumblingOverlay)null, k);
        } else {
            submitNodeCollector.submitModelPart(this.model.plate(), poseStack, this.model.renderType(material.atlasLocation()), i, j, this.materials.get(material), false, bl, -1, (ModelFeatureRenderer.CrumblingOverlay)null, k);
        }

        poseStack.popPose();
    }

    public record Unbaked(ResourceLocation baseModel, ResourceLocation baseModelNoPat) implements SpecialModelRenderer.Unbaked {

        public static final MapCodec<BucklerShieldModelRenderer.Unbaked> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        ResourceLocation.CODEC.fieldOf("texture_banner").forGetter(BucklerShieldModelRenderer.Unbaked::baseModel),
                        ResourceLocation.CODEC.fieldOf("texture_default").forGetter(BucklerShieldModelRenderer.Unbaked::baseModelNoPat)
                ).apply(instance, BucklerShieldModelRenderer.Unbaked::new)
        );

        @Override
        public @NotNull MapCodec<BucklerShieldModelRenderer.Unbaked> type() {
            return CODEC;
        }

        @Override
        public @NotNull SpecialModelRenderer<?> bake(BakingContext bakingContext) {
            ModelPart root = bakingContext.entityModelSet().bakeLayer(BUCKLER_MODEL_LAYER);
            BucklerShieldLibModel model = new BucklerShieldLibModel(root);
            return new BucklerShieldModelRenderer(
                    this.baseModel,
                    this.baseModelNoPat,
                    model
            );
        }
    }
}
