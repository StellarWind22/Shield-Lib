package com.github.stellarwind22.shieldlib.lib.client.render;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.lib.client.model.SpikedHeaterShieldModel;
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

public class SpikedHeaterShieldModelRenderer implements ShieldModelRenderer {

    private final ResourceLocation baseModel, baseModelNoPat;
    private final SpikedHeaterShieldModel model;
    public static final ModelLayerLocation SPIKED_HEATER_MODEL_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "spiked_heater_shield"), "main");

    public SpikedHeaterShieldModelRenderer(ResourceLocation baseModel, ResourceLocation baseModelNoPat, SpikedHeaterShieldModel model) {
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

        public static final MapCodec<SpikedHeaterShieldModelRenderer.Unbaked> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        ResourceLocation.CODEC.fieldOf("texture_banner").forGetter(SpikedHeaterShieldModelRenderer.Unbaked::baseModel),
                        ResourceLocation.CODEC.fieldOf("texture_default").forGetter(SpikedHeaterShieldModelRenderer.Unbaked::baseModelNoPat)
                ).apply(instance, SpikedHeaterShieldModelRenderer.Unbaked::new)
        );

        @Override
        public @NotNull MapCodec<SpikedHeaterShieldModelRenderer.Unbaked> type() {
            return CODEC;
        }

        @Override
        public @NotNull SpecialModelRenderer<?> bake(EntityModelSet entityModelSet) {
            ModelPart root = entityModelSet.bakeLayer(SPIKED_HEATER_MODEL_LAYER);
            SpikedHeaterShieldModel model = new SpikedHeaterShieldModel(root);
            return new SpikedHeaterShieldModelRenderer(
                    this.baseModel,
                    this.baseModelNoPat,
                    model
            );
        }
    }
}
