package com.github.stellarwind22.shieldlib.lib.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector2i;

@Environment(EnvType.CLIENT)
public class VanillaShieldModel extends Model implements ShieldModel {

    private final ModelPart plate;
    private final ModelPart handle;

    public VanillaShieldModel(ModelPart root) {
        super(root, RenderType::entitySolid);
        this.plate = root.getChild("plate");
        this.handle = root.getChild("handle");
    }

    @Override
    public RenderType getRenderType(ResourceLocation location) {
        return this.renderType(location);
    }

    @Override
    public ModelPart getRoot() {
        return this.root;
    }

    @Override
    public ModelPart handle() {
        return this.handle;
    }

    @Override
    public ModelPart plate() {
        return this.plate;
    }

    @Override
    public Shape shape() {
        return new Shape("vanilla", new Vector2i(2, 2));
    }
}
