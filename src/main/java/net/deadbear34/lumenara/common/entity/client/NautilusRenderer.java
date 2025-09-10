package net.deadbear34.lumenara.common.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.deadbear34.lumenara.Lumenara;
import net.deadbear34.lumenara.common.entity.custom.NautilusEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class NautilusRenderer extends MobRenderer<NautilusEntity, NautilusModel<NautilusEntity>> {
    public NautilusRenderer(EntityRendererProvider.Context context) {
        super(context, new NautilusModel<>(context.bakeLayer(NautilusModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(NautilusEntity nautilusEntity) {
        return ResourceLocation.fromNamespaceAndPath(Lumenara.MOD_ID,"textures/entity/nautilus/nautilus.png");
    }

    @Override
    public void render(NautilusEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}