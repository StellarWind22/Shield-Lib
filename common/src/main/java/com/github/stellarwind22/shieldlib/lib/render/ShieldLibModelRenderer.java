package com.github.stellarwind22.shieldlib.lib.render;

import com.github.stellarwind22.shieldlib.lib.model.ShieldLibModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Objects;
import java.util.Set;

@Environment(EnvType.CLIENT)
public class ShieldLibModelRenderer implements SpecialModelRenderer<DataComponentMap> {

    private final ResourceLocation baseModel, baseModelNoPat;
    private final ShieldLibModel model;

    public ShieldLibModelRenderer(ResourceLocation baseModel, ResourceLocation baseModelNoPat, ShieldLibModel model) {
        this.baseModel = baseModel;
        this.baseModelNoPat = baseModelNoPat;
        this.model = model;
    }

    @Nullable
    public DataComponentMap extractArgument(ItemStack itemStack) {
        return itemStack.getComponents();
    }

    @Override
    public void render(@Nullable DataComponentMap componentMap, ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, boolean bl) {
        BannerPatternLayers bannerPatternLayers = componentMap == null ? BannerPatternLayers.EMPTY:
                componentMap.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);

        DyeColor color = componentMap == null ? null : (DyeColor) componentMap.get(DataComponents.BASE_COLOR);

        boolean bl2 = !bannerPatternLayers.layers().isEmpty() || color != null;

        poseStack.pushPose();
        poseStack.scale(1.0F, -1.0F, -1.0F);

        try {

            @SuppressWarnings("deprecation")
            Material spriteMat = bl2
                    ? new Material(TextureAtlas.LOCATION_BLOCKS, this.baseModel)
                    : new Material(TextureAtlas.LOCATION_BLOCKS, this.baseModelNoPat);

            VertexConsumer vertexConsumer = spriteMat.sprite()
                    .wrap(ItemRenderer.getFoilBuffer(multiBufferSource,
                            model.renderType(spriteMat.atlasLocation()), itemDisplayContext == ItemDisplayContext.GUI, bl));

            model.handle().render(poseStack, vertexConsumer, i, j);

            if(bl2) {
                renderPatterns(poseStack, multiBufferSource, i, j, model.plate(),
                        spriteMat, false, (DyeColor) Objects.requireNonNullElse(color, DyeColor.WHITE),
                        bannerPatternLayers, bl, false);
            } else {
                model.plate().render(poseStack, vertexConsumer, i, j);
            }
        } finally {
            poseStack.popPose();
        }
    }

    //TODO: REWORK FOR CUSTOM SIZED BANNER TEXTURES
    public static void renderPatterns(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, ModelPart modelPart, Material material, boolean bl, DyeColor dyeColor, BannerPatternLayers bannerPatternLayers, boolean bl2, boolean bl3) {
        modelPart.render(poseStack, material.buffer(multiBufferSource, RenderType::entitySolid, bl3, bl2), i, j);
        renderPatternLayer(poseStack, multiBufferSource, i, j, modelPart, bl ? Sheets.BANNER_BASE : Sheets.SHIELD_BASE, dyeColor);

        for(int k = 0; k < 16 && k < bannerPatternLayers.layers().size(); ++k) {
            BannerPatternLayers.Layer layer = bannerPatternLayers.layers().get(k);
            Material material2 = bl ? Sheets.getBannerMaterial(layer.pattern()) : Sheets.getShieldMaterial(layer.pattern());
            renderPatternLayer(poseStack, multiBufferSource, i, j, modelPart, material2, layer.color());
        }

    }

    private static void renderPatternLayer(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, ModelPart modelPart, Material material, DyeColor dyeColor) {
        int k = dyeColor.getTextureDiffuseColor();
        modelPart.render(poseStack, material.buffer(multiBufferSource, RenderType::entityNoOutline), i, j, k);
    }

    @Override
    public void getExtents(Set<Vector3f> set) {
        PoseStack poseStack = new PoseStack();
        poseStack.scale(1.0F, -1.0F, -1.0F);
        this.model.root().getExtentsForGui(poseStack, set);
    }

    public record Unbaked(ResourceLocation baseModel, ResourceLocation baseModelNoPat) implements SpecialModelRenderer.Unbaked {

        public static final MapCodec<ShieldLibModelRenderer.Unbaked> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        ResourceLocation.CODEC.fieldOf("texture_banner").forGetter(ShieldLibModelRenderer.Unbaked::baseModel),
                        ResourceLocation.CODEC.fieldOf("texture_default").forGetter(ShieldLibModelRenderer.Unbaked::baseModelNoPat)
                ).apply(instance, ShieldLibModelRenderer.Unbaked::new)
        );

        @Override
        public @NotNull MapCodec<ShieldLibModelRenderer.Unbaked> type() {
            return CODEC;
        }

        @Override
        public @NotNull SpecialModelRenderer<?> bake(EntityModelSet entityModelSet) {
            ModelPart root = entityModelSet.bakeLayer(ModelLayers.SHIELD);

            ShieldLibModel model = new ShieldLibModel(root);
            return new ShieldLibModelRenderer(
                    this.baseModel,
                    this.baseModelNoPat,
                    model
            );
        }
    }
}
