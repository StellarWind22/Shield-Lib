package com.github.stellarwind22.shieldlib.lib.client.render;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.lib.client.model.SpikedTowerShieldModel;
import com.github.stellarwind22.shieldlib.lib.client.model.ShieldModel;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SpikedTowerShieldModelRenderer implements ShieldModelRenderer {

    private final ResourceLocation baseModel, baseModelNoPat;
    private final SpikedTowerShieldModel model;
    public static final ModelLayerLocation SPIKED_TOWER_MODEL_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "spiked_tower_shield"), "main");

    public SpikedTowerShieldModelRenderer(ResourceLocation baseModel, ResourceLocation baseModelNoPat, SpikedTowerShieldModel model) {
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

    public record Unbaked(ResourceLocation baseModel, ResourceLocation baseModelNoPat) implements SpecialModelRenderer.Unbaked {

        public static final MapCodec<SpikedTowerShieldModelRenderer.Unbaked> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        ResourceLocation.CODEC.fieldOf("texture_banner").forGetter(SpikedTowerShieldModelRenderer.Unbaked::baseModel),
                        ResourceLocation.CODEC.fieldOf("texture_default").forGetter(SpikedTowerShieldModelRenderer.Unbaked::baseModelNoPat)
                ).apply(instance, SpikedTowerShieldModelRenderer.Unbaked::new)
        );

        @Override
        public @NotNull MapCodec<SpikedTowerShieldModelRenderer.Unbaked> type() {
            return CODEC;
        }

        @Override
        public @NotNull SpecialModelRenderer<?> bake(EntityModelSet entityModelSet) {
            ModelPart root = entityModelSet.bakeLayer(SPIKED_TOWER_MODEL_LAYER);
            SpikedTowerShieldModel model = new SpikedTowerShieldModel(root);
            return new SpikedTowerShieldModelRenderer(
                    this.baseModel,
                    this.baseModelNoPat,
                    model
            );
        }
    }
}
