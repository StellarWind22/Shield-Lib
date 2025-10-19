package com.github.stellarwind22.shieldlib.lib.client.render;

import com.github.stellarwind22.shieldlib.lib.client.model.TowerShieldModel;
import com.github.stellarwind22.shieldlib.lib.client.model.ShieldModel;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class TowerShieldModelRenderer implements ShieldModelRenderer {

    private final MaterialSet materialSet;
    private final ResourceLocation baseModel, baseModelNoPat;
    private final TowerShieldModel model;

    public TowerShieldModelRenderer(MaterialSet materialSet, ResourceLocation baseModel, ResourceLocation baseModelNoPat, TowerShieldModel model) {
        this.materialSet = materialSet;
        this.baseModel = baseModel;
        this.baseModelNoPat = baseModelNoPat;
        this.model = model;
    }

    @Override
    public MaterialSet materialSet() { return this.materialSet; }

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

    public record Unbaked(ResourceLocation baseModel, ResourceLocation baseModelNoPat) implements SpecialModelRenderer.Unbaked {

        public static final MapCodec<TowerShieldModelRenderer.Unbaked> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        ResourceLocation.CODEC.fieldOf("texture_banner").forGetter(TowerShieldModelRenderer.Unbaked::baseModel),
                        ResourceLocation.CODEC.fieldOf("texture_default").forGetter(TowerShieldModelRenderer.Unbaked::baseModelNoPat)
                ).apply(instance, TowerShieldModelRenderer.Unbaked::new)
        );

        @Override
        public @NotNull MapCodec<TowerShieldModelRenderer.Unbaked> type() {
            return CODEC;
        }

        @Override
        public @NotNull SpecialModelRenderer<?> bake(BakingContext bakingContext) {
            ModelPart root = bakingContext.entityModelSet().bakeLayer(ModelLayers.SHIELD);
            TowerShieldModel model = new TowerShieldModel(root);
            return new TowerShieldModelRenderer(
                    bakingContext.materials(),
                    this.baseModel,
                    this.baseModelNoPat,
                    model
            );
        }
    }
}
