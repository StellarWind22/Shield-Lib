package com.github.stellarwind22.shieldlib.lib.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector2i;


public interface ShieldModel {

    RenderType getRenderType(ResourceLocation location);
    ModelPart getRoot();
    ModelPart handle();
    ModelPart plate();
    ResourceLocation shape();
}
