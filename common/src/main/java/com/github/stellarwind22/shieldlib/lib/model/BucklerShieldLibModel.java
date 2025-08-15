package com.github.stellarwind22.shieldlib.lib.model;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector2i;

@Environment(EnvType.CLIENT)
public class BucklerShieldLibModel extends Model implements ShieldModel {

    private final ModelPart plate;
    private final ModelPart handle;
    public static final ModelLayerLocation LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "buckler_shield"), "main");;

    public BucklerShieldLibModel(ModelPart root) {
        super(root, RenderType::entitySolid);
        this.plate = root.getChild("plate");
        this.handle = root.getChild("handle");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partDefinition = meshdefinition.getRoot();
        PartDefinition plate = partDefinition.addOrReplaceChild("plate", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -1.0F, -2.0F, 12.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));
        PartDefinition handle = partDefinition.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 2.0F, -1.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
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
        return new Shape("buckler", new Vector2i(2, 2));
    }
}
