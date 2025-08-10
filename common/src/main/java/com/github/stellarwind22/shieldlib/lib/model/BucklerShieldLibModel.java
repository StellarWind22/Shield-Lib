package com.github.stellarwind22.shieldlib.lib.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

@Environment(EnvType.CLIENT)
public class BucklerShieldLibModel extends ShieldLibModel {

    public BucklerShieldLibModel(ModelPart root) {
        super(root);
    }

    @Override
    public LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partDefinition = meshdefinition.getRoot();
        partDefinition.addOrReplaceChild("plate", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -6.0F, -2.0F, 12.0F, 12.0F, 1.0F), PartPose.offset(0.0F, 13.0F, -1.0F));
        partDefinition.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 6.0F), PartPose.offset(0.0F, 13.0F, -1.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override public String shape() { return "buckler"; }
}
