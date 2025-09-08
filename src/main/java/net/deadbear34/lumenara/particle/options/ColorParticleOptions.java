package net.deadbear34.lumenara.particle.options;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec; // <-- Import diubah
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.deadbear34.lumenara.particle.ModParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.joml.Vector3f;

public record ColorParticleOptions(Vector3f color) implements ParticleOptions {

    // UBAH TIPE DARI Codec MENJADI MapCodec
    public static final MapCodec<ColorParticleOptions> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.FLOAT.fieldOf("r").forGetter(options -> options.color().x()),
            Codec.FLOAT.fieldOf("g").forGetter(options -> options.color().y()),
            Codec.FLOAT.fieldOf("b").forGetter(options -> options.color().z())
    ).apply(instance, (r, g, b) -> new ColorParticleOptions(new Vector3f(r, g, b))));

    public static final StreamCodec<RegistryFriendlyByteBuf, ColorParticleOptions> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, options -> options.color().x(),
            ByteBufCodecs.FLOAT, options -> options.color().y(),
            ByteBufCodecs.FLOAT, options -> options.color().z(),
            (r, g, b) -> new ColorParticleOptions(new Vector3f(r, g, b))
    );

    @Override
    public ParticleType<?> getType() {
        return ModParticles.TEMPLATE_PARTICLES.get();
    }
}