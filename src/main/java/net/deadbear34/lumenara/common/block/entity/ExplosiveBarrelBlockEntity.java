package net.deadbear34.lumenara.common.block.entity;

import net.deadbear34.lumenara.client.particle.system.LumenaraParticles;
import net.deadbear34.lumenara.common.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ExplosiveBarrelBlockEntity extends BlockEntity {
    private static final int DEFAULT_FUSE_TICKS = 80; // 4 detik
    private int fuseTicks = -1;

    public ExplosiveBarrelBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.EXPLOSIVE_BARREL_ENTITY.get(), pPos, pBlockState);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (fuseTicks > 0) {
            fuseTicks--;
        } else if (fuseTicks == 0) {
            explode(level, pos);
        }
    }

    // Metode ini di-upgrade untuk bisa menerima durasi sumbu yang berbeda
    public void primeFuse(int ticks) {
        this.fuseTicks = ticks;
    }

// Di dalam metode explode() di ExplosiveBarrelBlockEntity.java

    private void explode(Level level, BlockPos pos) {
        if (!level.isClientSide) {
            level.removeBlock(pos, false);

            if (level instanceof ServerLevel serverLevel) {
                // --- UBAH PARAMETER DI BARIS INI ---
                serverLevel.sendParticles(LumenaraParticles.explosionDebris(),
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        300,  // Jumlah partikel (sebelumnya 100)
                        2.0, 2.0, 2.0, // Area penyebaran (sebelumnya 1.0)
                        1.0); // Kecepatan penyebaran (sebelumnya 0.5)
            }

            level.explode(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 4.0F, Level.ExplosionInteraction.BLOCK);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.putInt("Fuse", this.fuseTicks);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        this.fuseTicks = pTag.getInt("Fuse");
    }
}