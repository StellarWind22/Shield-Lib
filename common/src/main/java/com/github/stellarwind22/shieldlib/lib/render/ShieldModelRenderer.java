package com.github.stellarwind22.shieldlib.lib.render;

import com.github.stellarwind22.shieldlib.init.ShieldLibClient;
import com.github.stellarwind22.shieldlib.lib.model.ShieldModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Objects;
import java.util.Set;

public interface ShieldModelRenderer extends SpecialModelRenderer<DataComponentMap> {

    ResourceLocation baseModel();
    ResourceLocation baseModelNoPat();
    ShieldModel model();

    @Override
    default DataComponentMap extractArgument(ItemStack itemStack) {
        return itemStack.getComponents();
    }

    @Override
    default void getExtents(Set<Vector3f> set) {
        PoseStack poseStack = new PoseStack();
        poseStack.scale(1.0F, -1.0F, -1.0F);
        this.model().getRoot().getExtentsForGui(poseStack, set);
    }

    @Override
    default void render(@Nullable DataComponentMap componentMap, ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, boolean bl) {

        BannerPatternLayers bannerPatternLayers = componentMap == null ? BannerPatternLayers.EMPTY : componentMap.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);

        DyeColor color = componentMap == null ? null : componentMap.get(DataComponents.BASE_COLOR);

        boolean bl2 = !bannerPatternLayers.layers().isEmpty() || color != null;

        poseStack.pushPose();
        poseStack.scale(1.0F, -1.0F, -1.0F);

        try {

            Material spriteMat = bl2 ? new Material(ShieldLibClient.SHIELD_ATLAS_LOCATION, this.baseModel()) : new Material(ShieldLibClient.SHIELD_ATLAS_LOCATION, this.baseModelNoPat());

            VertexConsumer vertexConsumer = spriteMat.sprite()
                    .wrap(ItemRenderer.getFoilBuffer(multiBufferSource,
                            this.model().getRenderType(spriteMat.atlasLocation()), itemDisplayContext == ItemDisplayContext.GUI, bl));

            this.model().handle().render(poseStack, vertexConsumer, i, j);

            if(bl2) {
                renderPatterns(poseStack, multiBufferSource, i, j, this.model().plate(),
                        spriteMat, Objects.requireNonNullElse(color, DyeColor.WHITE),
                        bannerPatternLayers, bl, false);
            } else {
                this.model().plate().render(poseStack, vertexConsumer, i, j);
            }
        } finally {
            poseStack.popPose();
        }
    }

    default void renderPatterns(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, ModelPart modelPart, Material material, DyeColor dyeColor, BannerPatternLayers bannerPatternLayers, boolean bl2, boolean bl3) {
        modelPart.render(poseStack, material.buffer(multiBufferSource, RenderType::entitySolid, bl3, bl2), i, j);
        renderPatternLayer(poseStack, multiBufferSource, i, j, modelPart, ShieldLibClient.getShapedBannerMaterial(this.model().shape(), ResourceLocation.withDefaultNamespace("base")), dyeColor);

        for(int k = 0; k < 16 && k < bannerPatternLayers.layers().size(); ++k) {
            BannerPatternLayers.Layer layer = bannerPatternLayers.layers().get(k);
            Material material2 = ShieldLibClient.getShapedBannerMaterial(this.model().shape(), layer.pattern());
            renderPatternLayer(poseStack, multiBufferSource, i, j, modelPart, material2, layer.color());
        }

    }

    default void renderPatternLayer(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, ModelPart modelPart, Material material, DyeColor dyeColor) {
        int k = dyeColor.getTextureDiffuseColor();
        modelPart.render(poseStack, material.buffer(multiBufferSource, RenderType::entityNoOutline), i, j, k);
    }
}
