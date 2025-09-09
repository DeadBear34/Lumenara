package net.deadbear34.lumenara.particle;

import net.deadbear34.lumenara.particle.options.LumenaraParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import org.jetbrains.annotations.Nullable;

public class TemplateParticles extends TextureSheetParticle {

    // Simpan options untuk mengakses propertinya
    protected final LumenaraParticleOptions options;

    protected TemplateParticles(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, LumenaraParticleOptions options) {
        super(level, x, y, z, 0, 0, 0);

        this.options = options;

        // Atur semua properti dari options
        this.lifetime = options.lifetime;
        this.gravity = options.gravity;
        this.friction = 0.98f;
        this.quadSize = options.initialScale;
        this.xd = options.motion.x() + (this.random.nextFloat() - 0.5f) * 0.1f;
        this.yd = options.motion.y() + (this.random.nextFloat() - 0.5f) * 0.1f;
        this.zd = options.motion.z() + (this.random.nextFloat() - 0.5f) * 0.1f;
        this.setColor(options.initialColor.x(), options.initialColor.y(), options.initialColor.z());
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void tick() {
        super.tick();
        updateColorAndScale();
    }

    private void updateColorAndScale() {
        float progress = (float)this.age / (float)this.lifetime;

        float r = options.initialColor.x() + (options.finalColor.x() - options.initialColor.x()) * progress;
        float g = options.initialColor.y() + (options.finalColor.y() - options.initialColor.y()) * progress;
        float b = options.initialColor.z() + (options.finalColor.z() - options.initialColor.z()) * progress;
        this.setColor(r, g, b);

        this.quadSize = options.initialScale + (options.finalScale - options.initialScale) * progress;
    }

    @Override
    public ParticleRenderType getRenderType() { return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT; }
    @Override
    protected int getLightColor(float partialTick) { return 0xF000F0; }

    public static class Provider implements ParticleProvider<LumenaraParticleOptions> {
        private final SpriteSet spriteSet;
        public Provider(SpriteSet spriteSet) { this.spriteSet = spriteSet; }

        @Nullable
        @Override
        public Particle createParticle(LumenaraParticleOptions options, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            return new TemplateParticles(level, x, y, z, this.spriteSet, options);
        }
    }
}