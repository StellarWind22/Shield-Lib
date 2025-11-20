package com.github.stellarwind22.shieldlib.lib.client.render;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.lib.client.model.HeaterShieldModel;
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

public class HeaterShieldModelRenderer implements ShieldModelRenderer {

    private final ResourceLocation baseModel, baseModelNoPat;
    private final HeaterShieldModel model;
    public static final ModelLayerLocation HEATER_MODEL_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "heater_shield"), "main");

    public HeaterShieldModelRenderer(ResourceLocation baseModel, ResourceLocation baseModelNoPat, HeaterShieldModel model) {
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

        public static final MapCodec<HeaterShieldModelRenderer.Unbaked> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        ResourceLocation.CODEC.fieldOf("texture_banner").forGetter(HeaterShieldModelRenderer.Unbaked::baseModel),
                        ResourceLocation.CODEC.fieldOf("texture_default").forGetter(HeaterShieldModelRenderer.Unbaked::baseModelNoPat)
                ).apply(instance, HeaterShieldModelRenderer.Unbaked::new)
        );

        @Override
        public @NotNull MapCodec<HeaterShieldModelRenderer.Unbaked> type() {
            return CODEC;
        }

        @Override
        public @NotNull SpecialModelRenderer<?> bake(EntityModelSet entityModelSet) {
            ModelPart root = entityModelSet.bakeLayer(HEATER_MODEL_LAYER);
            HeaterShieldModel model = new HeaterShieldModel(root);
            return new HeaterShieldModelRenderer(
                    this.baseModel,
                    this.baseModelNoPat,
                    model
            );
        }
    }
}
