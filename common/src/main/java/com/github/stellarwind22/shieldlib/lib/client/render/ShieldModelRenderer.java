package com.github.stellarwind22.shieldlib.lib.client.render;

import com.github.stellarwind22.shieldlib.init.ShieldLibClient;
import com.github.stellarwind22.shieldlib.lib.client.model.ShieldModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
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
    default void submit(@Nullable DataComponentMap componentMap, ItemDisplayContext itemDisplayContext, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, int j, boolean bl, int k) {

        BannerPatternLayers bannerPatternLayers = componentMap == null ? BannerPatternLayers.EMPTY : componentMap.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);

        DyeColor color = componentMap == null ? null : componentMap.get(DataComponents.BASE_COLOR);

        boolean bl2 = !bannerPatternLayers.layers().isEmpty() || color != null;

        poseStack.pushPose();
        poseStack.scale(1.0F, -1.0F, -1.0F);


        Material spriteMat = bl2 ? new Material(ShieldLibClient.SHIELD_ATLAS_LOCATION, this.baseModel()) : new Material(ShieldLibClient.SHIELD_ATLAS_LOCATION, this.baseModelNoPat());

        submitNodeCollector.submitModelPart(this.model().handle(), poseStack, this.model().getRenderType(spriteMat.atlasLocation()), i, j, this.materials.);
        if(bl2) {
            this.submitPatterns(poseStack, submitNodeCollector, i, j, this.model().plate(), Unit.INSTANCE, spriteMat, Objects.requireNonNullElse(color, DyeColor.WHITE), bannerPatternLayers, bl, false);
        } else {
            this.model().plate().render(poseStack, vertexConsumer, i, j);
        }

            poseStack.popPose();
    }

    default void submitPatterns(MaterialSet materialSet, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int j, int k, Model<Unit> model, Unit object, Material material, boolean bl, DyeColor arg6, BannerPatternLayers arg7, boolean bl2, @Nullable ModelFeatureRenderer.CrumblingOverlay arg8, int l) {
        submitNodeCollector.submitModel(model, object, poseStack, material.renderType(RenderType::entitySolid), j, k, -1, materialSet.get(material), l, arg8);
        if (bl2) {
            submitNodeCollector.submitModel(model, object, poseStack, RenderType.entityGlint(), j, k, -1, materialSet.get(material), 0, arg8);
        }

        this.submitPatternLayer(materialSet, poseStack, submitNodeCollector, j, k, model, object, bl ? Sheets.BANNER_BASE : Sheets.SHIELD_BASE, arg6, arg8);

        for(int i = 0; i < 16 && i < arg7.layers().size(); ++i) {
            BannerPatternLayers.Layer bannerpatternlayers$layer = arg7.layers().get(i);
            Material material2 = bl ? Sheets.getBannerMaterial(bannerpatternlayers$layer.pattern()) : Sheets.getShieldMaterial(bannerpatternlayers$layer.pattern());
            submitPatternLayer(materialSet, poseStack, submitNodeCollector, j, k, model, object, material2, bannerpatternlayers$layer.color(), null);
        }

    }

    default void submitPatternLayer(MaterialSet materialSet, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int j, int k, Model<Unit> model, Unit object, Material material, DyeColor dyeColor, @Nullable ModelFeatureRenderer.CrumblingOverlay overlay) {
        int color = dyeColor.getTextureDiffuseColor();
        submitNodeCollector.submitModel(model, object, poseStack, material.renderType(RenderType::entityNoOutline), j, k, color, materialSet.get(material), 0, overlay);
    }
}
