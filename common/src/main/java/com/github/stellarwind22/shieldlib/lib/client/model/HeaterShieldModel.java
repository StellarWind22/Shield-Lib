package com.github.stellarwind22.shieldlib.lib.client.model;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class HeaterShieldModel extends Model implements ShieldModel {

    private final ModelPart handle;
    private final ModelPart plate;
    public static final ModelLayerLocation LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "heater_shield"), "main");

    public HeaterShieldModel(ModelPart root) {
        super(root, RenderType::entitySolid);
        this.handle = root.getChild("handle");
        this.plate = root.getChild("plate");
    }
    
    public static LayerDefinition createLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("plate", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -1.0F, -2.0F, 12.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 11).addBox(-5.0F, 9.0F, -2.0F, 10.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 16).addBox(-3.0F, 13.0F, -2.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));
        partDefinition.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override public RenderType getRenderType(ResourceLocation location) {return this.renderType(location);}
    @Override public ModelPart getRoot() {return this.root;}
    @Override public ModelPart handle() { return this.handle; }
    @Override public ModelPart plate() { return this.plate; }
    @Override public String shape() { return "heater"; }
}
