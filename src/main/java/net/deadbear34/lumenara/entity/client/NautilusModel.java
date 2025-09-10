package net.deadbear34.lumenara.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.deadbear34.lumenara.entity.custom.NautilusEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class NautilusModel<T extends NautilusEntity> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("modid", "nautilus"), "main");
    private final ModelPart shell;
    private final ModelPart head;
    private final ModelPart tentacles;

    public NautilusModel(ModelPart root) {
        this.shell = root.getChild("shell");
        this.head = root.getChild("head");
        this.tentacles = this.head.getChild("tentacles");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition shell = partdefinition.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -5.0F, 1.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-2.0F, -5.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.0F, -2.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(10, 8).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 23.0F, -2.0F));

        PartDefinition tentacles = head.addOrReplaceChild("tentacles", CubeListBuilder.create().texOffs(12, 6).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(12, 0).addBox(1.0F, 0.0F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 3).addBox(-1.0F, 0.0F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 7).addBox(-1.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(NautilusEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch);

        this.animate(entity.idleAnimationState, NautilusAnimations.ANIM_IDLE_NAUTILUS, ageInTicks, 1f);
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45);

        this.head.yRot = headYaw * ((float)Math.PI / 180f);
        this.head.xRot = headPitch *  ((float)Math.PI / 180f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        shell.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return shell;
    }
}
