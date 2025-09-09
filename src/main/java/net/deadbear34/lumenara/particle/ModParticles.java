package net.deadbear34.lumenara.particle;

import com.mojang.serialization.MapCodec;
import net.deadbear34.lumenara.Lumenara;
import net.deadbear34.lumenara.particle.options.LumenaraParticleOptions; // Import yang baru
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Lumenara.MOD_ID);

    // Kelas turunan ini sekarang menggunakan LumenaraParticleOptions
    public static class LumenaraParticleType extends ParticleType<LumenaraParticleOptions> {
        public LumenaraParticleType() { super(false); }
        @Override public MapCodec<LumenaraParticleOptions> codec() { return LumenaraParticleOptions.CODEC; }
        @Override public StreamCodec<? super RegistryFriendlyByteBuf, LumenaraParticleOptions> streamCodec() { return LumenaraParticleOptions.STREAM_CODEC; }
    }

    // Ganti nama dan tipenya menjadi lebih deskriptif
    public static final Supplier<ParticleType<LumenaraParticleOptions>> TEMPLATE_PARTICLES =
            PARTICLE_TYPES.register("template_particles", LumenaraParticleType::new);

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}