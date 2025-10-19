package com.github.stellarwind22.shieldlib.lib.client.render;

import com.github.stellarwind22.shieldlib.init.ShieldLibClient;
import com.github.stellarwind22.shieldlib.lib.client.model.ShieldModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
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

    MaterialSet materialSet();
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

    //Based off of ShieldSpecialRenderer
    @Override
    default void submit(@Nullable DataComponentMap componentMap, ItemDisplayContext itemDisplayContext, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, int j, boolean bl, int k) {

        BannerPatternLayers bannerPatternLayers = componentMap == null ? BannerPatternLayers.EMPTY : componentMap.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);

        DyeColor color = componentMap == null ? null : componentMap.get(DataComponents.BASE_COLOR);

        boolean bl2 = !bannerPatternLayers.layers().isEmpty() || color != null;

        poseStack.pushPose();
        poseStack.scale(1.0F, -1.0F, -1.0F);


        Material spriteMat = bl2 ? new Material(ShieldLibClient.SHIELD_ATLAS_LOCATION, this.baseModel()) : new Material(ShieldLibClient.SHIELD_ATLAS_LOCATION, this.baseModelNoPat());

        submitNodeCollector.submitModelPart(
                this.model().handle(),
                poseStack,
                this.model().getRenderType(spriteMat.atlasLocation()),
                i, j,
                this.materialSet().get(spriteMat),
                false, bl, -1,
                null, k
        );

        if(bl2) {

            this.submitPatterns(
                    submitNodeCollector,
                    this.model().plate(),
                    poseStack,
                    i, j,
                    spriteMat,
                    false,
                    Objects.requireNonNullElse(
                            color,
                            DyeColor.WHITE
                    ),
                    bannerPatternLayers,
                    bl, null, k
            );

        } else {

            submitNodeCollector.submitModelPart(
                    this.model().plate(),
                    poseStack,
                    this.model().getRenderType(spriteMat.atlasLocation()),
                    i, j,
                    this.materialSet().get(spriteMat),
                    false, bl, -1,
                    null, k
            );
        }

        poseStack.popPose();
    }

    default void submitPatterns(SubmitNodeCollector submitNodeCollector, ModelPart modelPart, PoseStack poseStack, int i, int j, Material material, boolean bl, DyeColor dyeColor, BannerPatternLayers arg7, boolean bl2, @Nullable ModelFeatureRenderer.CrumblingOverlay overlay, int k) {
        submitNodeCollector.submitModelPart(
                modelPart,
                poseStack,
                material.renderType(RenderType::entitySolid),
                i, j,
                this.materialSet().get(material),
                false, bl, -1,
                overlay, k
        );

        if (bl2) {
            submitNodeCollector.submitModelPart(modelPart, poseStack, RenderType.entityGlint(), i, j, this.materialSet().get(material), false, bl, -1, overlay, k);
        }

        this.submitPatternLayer(
                submitNodeCollector,
                modelPart,
                poseStack,
                i, j,
                ShieldLibClient.getShapedBannerMaterial(this.model().shape(), ResourceLocation.withDefaultNamespace("base")),
                false, dyeColor,
                overlay
        );

        for(int b = 0; b < 16 && b < arg7.layers().size(); ++b) {
            BannerPatternLayers.Layer layer = arg7.layers().get(b);
            Material material2 = ShieldLibClient.getShapedBannerMaterial(this.model().shape(), layer.pattern());
            submitPatternLayer(
                    submitNodeCollector,
                    modelPart,
                    poseStack,
                    i, j,
                    material2,
                    false, layer.color(),
                    overlay
            );
        }

    }

    default void submitPatternLayer(SubmitNodeCollector submitNodeCollector, ModelPart modelPart, PoseStack poseStack, int i, int j, Material material, boolean bl, DyeColor dyeColor, @Nullable ModelFeatureRenderer.CrumblingOverlay overlay) {
        int color = dyeColor.getTextureDiffuseColor();
        submitNodeCollector.submitModelPart(
                modelPart,
                poseStack,
                material.renderType(RenderType::entityNoOutline),
                i, j,
                this.materialSet().get(material),
                false, bl, -1,
                overlay, color
        );
    }
}
