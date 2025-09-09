package net.deadbear34.lumenara.particle.system;

import net.deadbear34.lumenara.particle.options.LumenaraParticleOptions;
import org.joml.Vector3f;

public class LumenaraParticles {

    public static LumenaraParticleOptions simple(Vector3f color, int lifetime) {
        return new LumenaraParticleOptions(color, color, new Vector3f(0,0,0), lifetime, 0.01f, 0.2f, 0.0f);
    }

    // Preset untuk partikel Api Roh
    public static LumenaraParticleOptions soulFire() {
        return new LumenaraParticleOptions(
                new Vector3f(0.2f, 0.8f, 1.0f), // Warna Awal (Cyan cerah)
                new Vector3f(0.1f, 0.4f, 0.8f), // Warna Akhir (Biru gelap)
                new Vector3f(0, 0.15f, 0),      // Gerakan Awal (cepat ke atas)
                10,                             // Masa Hidup
                0f,                             // Gravitasi
                0.05f,                          // Ukuran Awal (DIUBAH DARI 0.15f menjadi 0.05f)
                0.0f                            // Ukuran Akhir
        );
    }


    public static LumenaraParticleOptions rocketBurst() {
        return new LumenaraParticleOptions(
                new Vector3f(1.0f, 0.7f, 0.2f), // Warna Awal (Oranye-kuning)
                new Vector3f(0.8f, 0.2f, 0.0f), // Warna Akhir (Merah-bara)
                new Vector3f(0, 0.4f, 0),      // Gerakan Awal (dibuat lebih cepat)
                15,                             // Masa Hidup (sedikit lebih lama agar terlihat)
                0f,                             // Gravitasi
                0.1f,                           // Ukuran Awal (sedikit lebih besar)
                0.0f                            // Ukuran Akhir
        );
    }

    public static LumenaraParticleOptions explosionDebris() {
        return new LumenaraParticleOptions(
                new Vector3f(1.0f, 1.0f, 1.0f), // Warna Awal (Putih)
                new Vector3f(0.8f, 0.8f, 0.8f), // Warna Akhir (Abu-abu muda, untuk efek pudar)
                new Vector3f(0, 0, 0),          // Gerakan Awal
                30,                             // Masa Hidup (sedikit lebih lama)
                0.03f,                          // Gravitasi (jatuh perlahan)
                0.4f,                           // Ukuran Awal
                0.0f                            // Ukuran Akhir (menghilang)
        );
    }
}