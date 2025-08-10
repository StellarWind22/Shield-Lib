package com.github.stellarwind22.shieldlib.lib.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import org.joml.Vector2i;

@Environment(EnvType.CLIENT)
public class ShieldLibModel extends Model {

    private final ModelPart plate;
    private final ModelPart handle;

    public ShieldLibModel(ModelPart root) {
        super(root, RenderType::entitySolid);
        this.plate = root.getChild("plate");
        this.handle = root.getChild("handle");
    }

    public LayerDefinition createLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("plate", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -11.0F, -2.0F, 12.0F, 22.0F, 1.0F), PartPose.ZERO);
        partDefinition.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 6.0F), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    public ModelPart                            handle()        {   return this.handle;              }
    public ModelPart                            plate()         {   return this.plate;               }
    public String                               shape()         {   return "vanilla";                }
    public Vector2i                             bannerStart()   {   return new Vector2i(2, 2); }
}
