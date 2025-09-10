package net.deadbear34.lumenara.common.entity.custom;

import net.deadbear34.lumenara.common.entity.ai.goal.NautilusDefensiveGoal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;

public class NautilusEntity extends WaterAnimal {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState hideAnimationState = new AnimationState();
    public final AnimationState unhideAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;

    private int hideCooldown = 0;

    private static final EntityDataAccessor<Boolean> DATA_HIDING =
            SynchedEntityData.defineId(NautilusEntity.class, EntityDataSerializers.BOOLEAN);

    public NautilusEntity(EntityType<? extends WaterAnimal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }


    @Override
    protected void registerGoals() {
        // Defensive lebih prioritas daripada swimming
        this.goalSelector.addGoal(0, new NautilusDefensiveGoal(this, 6.0D));

        this.goalSelector.addGoal(1, new RandomSwimmingGoal(this, 1.0D, 40) {
            @Override
            public boolean canUse() {
                return super.canUse() && NautilusEntity.this.isInWater();
            }

            @Override
            protected Vec3 getPosition() {
                Vec3 vec3 = NautilusEntity.this.getViewVector(0.0F);
                int x = (int)(NautilusEntity.this.getX() + (NautilusEntity.this.getRandom().nextInt(15) - 7));
                int y = (int)(NautilusEntity.this.getY() + (NautilusEntity.this.getRandom().nextInt(6) - 3));
                int z = (int)(NautilusEntity.this.getZ() + (NautilusEntity.this.getRandom().nextInt(15) - 7));
                return new Vec3(x, y, z);
            }
        });

        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
    }


    public static AttributeSupplier.Builder createAttributes() {
        return WaterAnimal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 8)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FOLLOW_RANGE, 24.0D)
                .add(Attributes.ARMOR, 4.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2D);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean result = super.hurt(source, amount);
        if (!this.level().isClientSide) {
            this.setThreatened(true);
            this.hideCooldown = 100;
        }
        return result;
    }

    public void setThreatened(boolean value) {
        if (this.isHiding() != value) {
            this.entityData.set(DATA_HIDING, value);

            if (value) {
                this.getAttribute(Attributes.ARMOR).setBaseValue(8.0D);
            } else {
                this.getAttribute(Attributes.ARMOR).setBaseValue(4.0D);
            }
        }
    }


    @Override
    protected PathNavigation createNavigation(Level level) {
        return new WaterBoundPathNavigation(this, level);
    }


    @Override
    public boolean checkSpawnObstruction(LevelReader level) {
        return level.isWaterAt(this.blockPosition());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_HIDING, false);
    }


    private void setupAnimationStates(){
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void tick() {
        super.tick();

        // jalankan animasi
        if (this.level().isClientSide) {
            this.setupAnimationStates();

            if (this.isHiding() && !this.hideAnimationState.isStarted()) {
                this.hideAnimationState.start(this.tickCount);
            }
            if (!this.isHiding() && !this.unhideAnimationState.isStarted()) {
                this.unhideAnimationState.start(this.tickCount);
            }
        }

        if (this.isHiding()) {
            this.setNoGravity(false);
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.2, 1.0, 0.2));
        } else {
            this.setNoGravity(true);
        }

        if (this.hideCooldown > 0) {
            hideCooldown--;
            if (hideCooldown == 0) {
                this.setThreatened(false);
            }
        }

        if (!this.isInWater()) {
            this.hurt(this.damageSources().dryOut(), 1.0F);
        }
    }



    public boolean isHiding() {
        return this.entityData.get(DATA_HIDING);
    }

}