package net.deadbear34.lumenara.common.registry; // Sesuaikan package jika perlu

import net.deadbear34.lumenara.Lumenara;
import net.deadbear34.lumenara.common.block.entity.ExplosiveBarrelBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Lumenara.MOD_ID);

    // --- PENDAFTARAN BARU DI SINI ---
    public static final Supplier<BlockEntityType<ExplosiveBarrelBlockEntity>> EXPLOSIVE_BARREL_ENTITY =
            BLOCK_ENTITIES.register("explosive_barrel", () ->
                    BlockEntityType.Builder.of(ExplosiveBarrelBlockEntity::new,
                            // Tipe Block Entity ini hanya berlaku untuk blok Explosive Barrel
                            ModBlocks.EXPLOSIVE_BARREL.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}