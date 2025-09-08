package net.deadbear34.lumenara.particle;

import com.mojang.serialization.MapCodec;
import net.deadbear34.lumenara.Lumenara;
import net.deadbear34.lumenara.particle.options.ColorParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Lumenara.MOD_ID);

    public static class CustomParticleType extends ParticleType<ColorParticleOptions> {
        public CustomParticleType() {
            super(false);
        }

        @Override
        public MapCodec<ColorParticleOptions> codec() {
            return ColorParticleOptions.CODEC;
        }

        // TAMBAHKAN KEMBALI METODE INI
        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, ColorParticleOptions> streamCodec() {
            return ColorParticleOptions.STREAM_CODEC;
        }
    }

    public static final Supplier<ParticleType<ColorParticleOptions>> TEMPLATE_PARTICLES =
            PARTICLE_TYPES.register("template_particles", CustomParticleType::new);

    // TAMBAHKAN BARIS DI BAWAH INI
    public static final Supplier<SimpleParticleType> RED_FLAME_PARTICLE =
            PARTICLE_TYPES.register("red_flame_particle", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}