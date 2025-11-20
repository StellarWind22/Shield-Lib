package com.github.stellarwind22.shieldlib.lib.client.model;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class SpikedTargeShieldModel extends Model implements ShieldModel {

    private final ModelPart plate;
    private final ModelPart handle;
    public static final ModelLayerLocation LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "spiked_targe_shield"), "main");

    public SpikedTargeShieldModel(ModelPart root) {
        super(root, RenderType::entitySolid);
        this.plate = root.getChild("plate");
        this.handle = root.getChild("handle");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partDefinition = meshdefinition.getRoot();

        PartDefinition plate = partDefinition.addOrReplaceChild("plate", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -2.0F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(-6.0F, -9.0F, -2.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 19).addBox(-6.0F, 8.0F, -2.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 21).addBox(-9.0F, -6.0F, -2.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 21).addBox(8.0F, -6.0F, -2.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));


       plate.addOrReplaceChild("spikes", CubeListBuilder.create().texOffs(44, 0).addBox(-5.0F, -6.0F, -4.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(50, 0).addBox(-7.0F, 2.0F, -4.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(50, 3).addBox(-5.0F, 5.0F, -4.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(44, 0).addBox(4.0F, -6.0F, -4.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(50, 0).addBox(6.0F, 2.0F, -4.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(44, 3).addBox(-7.0F, -3.0F, -4.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(44, 3).addBox(6.0F, -3.0F, -4.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(50, 3).addBox(4.0F, 5.0F, -4.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        partDefinition.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override public RenderType getRenderType(ResourceLocation location) {return this.renderType(location);}
    @Override public ModelPart getRoot() {return this.root; }
    @Override public ModelPart handle() { return this.handle; }
    @Override public ModelPart plate() { return this.plate; }
    @Override public String shape() { return "targe"; }
}
