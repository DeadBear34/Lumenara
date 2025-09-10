package net.deadbear34.lumenara.entity.ai.goal;

import net.deadbear34.lumenara.entity.custom.NautilusEntity;
import net.minecraft.world.entity.ai.goal.Goal;


import java.util.EnumSet;

public class NautilusDefensiveGoal extends Goal {
    private final NautilusEntity nautilus;
    private final double radius;

    public NautilusDefensiveGoal(NautilusEntity nautilus, double radius) {
        this.nautilus = nautilus;
        this.radius = radius;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return nautilus.isHiding();
    }

    @Override
    public boolean canContinueToUse() {
        return nautilus.isHiding();
    }

    @Override
    public void start() {
        nautilus.getNavigation().stop();
    }

    @Override
    public void stop() {
        nautilus.setThreatened(false);
    }

    @Override
    public void tick() {
        nautilus.getNavigation().stop();
        if (nautilus.isHiding()) {
            nautilus.setDeltaMovement(nautilus.getDeltaMovement().add(0, -0.02, 0));
        }
    }

}

