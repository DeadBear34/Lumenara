package net.deadbear34.lumenara.common.entity.client;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class NautilusRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState hideAnimationState = new AnimationState();
    public final AnimationState unhideAnimationState = new AnimationState();

    public boolean isHiding;
    public float netHeadYaw;
    public float headPitch;
    public float ageInTicks;
}
