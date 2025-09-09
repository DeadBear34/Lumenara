package net.deadbear34.lumenara.particle.options;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.deadbear34.lumenara.particle.ModParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.joml.Vector3f;

public class LumenaraParticleOptions implements ParticleOptions {

    public final Vector3f initialColor;
    public final Vector3f finalColor;
    public final Vector3f motion;
    public final int lifetime;
    public final float gravity;
    public final float initialScale;
    public final float finalScale;

    private static final Codec<Vector3f> VECTOR3F_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("r").forGetter(Vector3f::x),
            Codec.FLOAT.fieldOf("g").forGetter(Vector3f::y),
            Codec.FLOAT.fieldOf("b").forGetter(Vector3f::z)
    ).apply(instance, Vector3f::new));

    public static final MapCodec<LumenaraParticleOptions> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            VECTOR3F_CODEC.fieldOf("initialColor").forGetter(d -> d.initialColor),
            VECTOR3F_CODEC.fieldOf("finalColor").forGetter(d -> d.finalColor),
            VECTOR3F_CODEC.fieldOf("motion").forGetter(d -> d.motion),
            Codec.INT.fieldOf("lifetime").forGetter(d -> d.lifetime),
            Codec.FLOAT.fieldOf("gravity").forGetter(d -> d.gravity),
            Codec.FLOAT.fieldOf("initialScale").forGetter(d -> d.initialScale),
            Codec.FLOAT.fieldOf("finalScale").forGetter(d -> d.finalScale)
    ).apply(instance, LumenaraParticleOptions::new));

    // MENGGANTI StreamCodec.composite DENGAN StreamCodec.of YANG LEBIH EKSPLISIT
    public static final StreamCodec<RegistryFriendlyByteBuf, LumenaraParticleOptions> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public LumenaraParticleOptions decode(RegistryFriendlyByteBuf buf) {
            // Cara membaca data dari buffer, URUTAN HARUS SAMA DENGAN ENCODE
            Vector3f initialColor = ByteBufCodecs.VECTOR3F.decode(buf);
            Vector3f finalColor = ByteBufCodecs.VECTOR3F.decode(buf);
            Vector3f motion = ByteBufCodecs.VECTOR3F.decode(buf);
            int lifetime = ByteBufCodecs.INT.decode(buf);
            float gravity = ByteBufCodecs.FLOAT.decode(buf);
            float initialScale = ByteBufCodecs.FLOAT.decode(buf);
            float finalScale = ByteBufCodecs.FLOAT.decode(buf);
            return new LumenaraParticleOptions(initialColor, finalColor, motion, lifetime, gravity, initialScale, finalScale);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, LumenaraParticleOptions options) {
            // Cara menulis data ke buffer, URUTAN HARUS SAMA DENGAN DECODE
            ByteBufCodecs.VECTOR3F.encode(buf, options.initialColor);
            ByteBufCodecs.VECTOR3F.encode(buf, options.finalColor);
            ByteBufCodecs.VECTOR3F.encode(buf, options.motion);
            ByteBufCodecs.INT.encode(buf, options.lifetime);
            ByteBufCodecs.FLOAT.encode(buf, options.gravity);
            ByteBufCodecs.FLOAT.encode(buf, options.initialScale);
            ByteBufCodecs.FLOAT.encode(buf, options.finalScale);
        }
    };

    public LumenaraParticleOptions(Vector3f initialColor, Vector3f finalColor, Vector3f motion, int lifetime, float gravity, float initialScale, float finalScale) {
        this.initialColor = initialColor;
        this.finalColor = finalColor;
        this.motion = motion;
        this.lifetime = lifetime;
        this.gravity = gravity;
        this.initialScale = initialScale;
        this.finalScale = finalScale;
    }

    @Override
    public ParticleType<?> getType() {
        return ModParticles.TEMPLATE_PARTICLES.get();
    }
}