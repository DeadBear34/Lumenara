package net.deadbear34.lumenara.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

// 1. Kelas ini adalah turunan (extends) dari TemplateParticles
public class RedFlameParticle extends TemplateParticles {

    // Konstruktor ini menerima parameter standar dari game
    protected RedFlameParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed) {
        // 2. Panggil konstruktor induk (super) dengan warna merah-oranye yang sudah ditentukan
        super(level, x, y, z, spriteSet, xSpeed, ySpeed, zSpeed, new Vector3f(1.0f, 0.4f, 0.1f));

        // Anda bisa mengubah properti lain di sini jika mau
        this.quadSize *= 0.85f; // Membuat partikel api sedikit lebih kecil
        this.lifetime = 20; // Api biasanya tidak bertahan lama
    }

    // 3. Override metode tick() untuk menambahkan perilaku khusus
    @Override
    public void tick() {
        super.tick(); // Menjalankan logika dasar dari TemplateParticles (lifetime, friksi, dll)

        // Menambahkan sedikit gerakan ke atas setiap tick, meniru perilaku api
        this.yd += 0.005D;
    }

    // 4. Setiap partikel butuh "Pabrik" (Provider) nya sendiri
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        // Metode ini yang akan dipanggil game untuk membuat partikel baru
        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            return new RedFlameParticle(level, x, y, z, this.spriteSet, xd, yd, zd);
        }
    }
}