package net.deadbear34.lumenara.common.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.deadbear34.lumenara.Lumenara;
import net.deadbear34.lumenara.common.entity.custom.NautilusEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class NautilusRenderer extends MobRenderer<NautilusEntity, NautilusRenderState, NautilusModel> {
    public NautilusRenderer(EntityRendererProvider.Context context) {
        super(context, new NautilusModel(context.bakeLayer(NautilusModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(NautilusRenderState entity) {
        return ResourceLocation.fromNamespaceAndPath(Lumenara.MOD_ID,"textures/entity/nautilus/nautilus.png");
    }


    @Override
    public void render(NautilusRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(renderState, poseStack, bufferSource, packedLight);
    }

    @Override
    public void extractRenderState(NautilusEntity entity, NautilusRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);

        // sinkronkan animasi dari entity ke render state
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.hideAnimationState.copyFrom(entity.hideAnimationState);
        state.unhideAnimationState.copyFrom(entity.unhideAnimationState);

        // sinkronkan variabel lain
        state.isHiding = entity.isHiding();
        state.netHeadYaw = entity.getYHeadRot();
        state.headPitch = entity.getXRot();
        state.ageInTicks = entity.tickCount + partialTick;
    }


    @Override
    public NautilusRenderState createRenderState() {
        return new NautilusRenderState();
    }
}