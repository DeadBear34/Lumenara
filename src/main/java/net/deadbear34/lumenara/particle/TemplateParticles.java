package net.deadbear34.lumenara.particle;

import net.deadbear34.lumenara.particle.options.ColorParticleOptions; // <-- IMPORT BARU
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f; // <-- IMPORT BARU

public class TemplateParticles extends TextureSheetParticle {
    // Konstruktor diubah untuk menerima warna
    protected TemplateParticles(ClientLevel level, double x, double y, double z, SpriteSet spriteSet,
                                double xSpeed, double ySpeed, double zSpeed, Vector3f color) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.friction = 0.8f;
        this.lifetime = 12;
        this.setSpriteFromAge(spriteSet);

        // Atur warna partikel
        this.setColor(color.x(), color.y(), color.z());
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    // Provider diubah untuk menangani ColorParticleOptions
    public static class Provider implements ParticleProvider<ColorParticleOptions> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(ColorParticleOptions options, ClientLevel clientLevel,
                                       double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            // Buat partikel baru dan teruskan warna dari options
            return new TemplateParticles(clientLevel, pX, pY, pZ, this.spriteSet, pXSpeed, pYSpeed, pZSpeed, options.color());
        }
    }
}