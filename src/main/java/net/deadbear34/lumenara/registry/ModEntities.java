package net.deadbear34.lumenara.registry;

import net.deadbear34.lumenara.Lumenara;
import net.deadbear34.lumenara.entity.custom.NautilusEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Lumenara.MOD_ID);

    public static final Supplier<EntityType<NautilusEntity>> NAUTILUS =
            ENTITY_TYPE.register("nautilus", () -> EntityType.Builder.of(NautilusEntity::new, MobCategory.WATER_CREATURE)
                    .sized(0.27f, 0.36f).build("nautilus"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPE.register(eventBus);
    }
}