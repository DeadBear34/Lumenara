package net.deadbear34.lumenara.entity.client;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class NautilusAnimations {

    public static final AnimationDefinition ANIM_IDLE_NAUTILUS = AnimationDefinition.Builder.withLength(0.5f).looping()
            .addAnimation("tentacles",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1.1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.16766666f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3433333f, KeyframeAnimations.scaleVec(1f, 0.95f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.scaleVec(1f, 1.1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR))).build();
}
