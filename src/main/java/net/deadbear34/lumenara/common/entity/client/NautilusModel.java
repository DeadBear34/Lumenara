package net.deadbear34.lumenara.common.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.deadbear34.lumenara.Lumenara;
import net.deadbear34.lumenara.common.entity.custom.NautilusEntity;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class NautilusModel extends EntityModel<NautilusRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION =  new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Lumenara.MOD_ID, "nautilus"), "main");

    private final ModelPart shell;
    private final ModelPart head;
    private final ModelPart tentacles;

    private final KeyframeAnimation hideAnimation;

    private final KeyframeAnimation unhideAnimation;
    private final KeyframeAnimation idlingAnimation;

    public NautilusModel(ModelPart root) {
        super(root);
        this.shell = root.getChild("shell");
        this.head = root.getChild("head");
        this.tentacles = this.head.getChild("tentacles");

        this.idlingAnimation = NautilusAnimations.ANIM_IDLE_NAUTILUS.bake(root);
        this.unhideAnimation = NautilusAnimations.UNHIDING_ON_SHELL_NAUTILUS.bake(root);
        this.hideAnimation = NautilusAnimations.HIDING_ON_SHELL_NAUTILUS.bake(root);
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
    public void setupAnim(NautilusRenderState state) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        // hanya rotasi kepala kalau tidak sedang hiding
        if (!state.isHiding) {
            this.applyHeadRotation(state.netHeadYaw, state.headPitch);
        }

        // idle anim
        idlingAnimation.apply(state.idleAnimationState, state.ageInTicks, 1f);

        // hiding / un-hiding anim
        hideAnimation.apply(state.hideAnimationState, state.ageInTicks, 1f);
        unhideAnimation.apply(state.unhideAnimationState, state.ageInTicks, 1f);
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45);

        this.head.yRot = headYaw * ((float)Math.PI / 180f);
        this.head.xRot = headPitch *  ((float)Math.PI / 180f);
    }

}