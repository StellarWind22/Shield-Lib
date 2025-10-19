package com.github.stellarwind22.shieldlib.lib.client.render;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.lib.client.model.BucklerShieldLibModel;
import com.github.stellarwind22.shieldlib.lib.client.model.ShieldModel;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class BucklerShieldModelRenderer implements ShieldModelRenderer {

    private final MaterialSet materialSet;
    private final ResourceLocation baseModel, baseModelNoPat;
    private final BucklerShieldLibModel model;

    public static final ModelLayerLocation BUCKLER_MODEL_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "buckler_shield"), "main");

    public BucklerShieldModelRenderer(MaterialSet materialSet, ResourceLocation baseModel, ResourceLocation baseModelNoPat, BucklerShieldLibModel model) {
        this.materialSet = materialSet;
        this.model = model;
        this.baseModel = baseModel;
        this.baseModelNoPat = baseModelNoPat;
    }

    @Override
    public MaterialSet materialSet() { return this.materialSet; }

    @Override
    public ResourceLocation baseModel() { return this.baseModel; }

    @Override
    public ResourceLocation baseModelNoPat() { return this.baseModelNoPat; }

    @Override
    public ShieldModel model() {
        return this.model;
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
                    bakingContext.materials(),
                    this.baseModel,
                    this.baseModelNoPat,
                    model
            );
        }
    }
}
