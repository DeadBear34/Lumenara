package net.deadbear34.lumenara.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.IShearable;

public class MiniGrassBlock extends VegetationBlock implements BonemealableBlock, IShearable {

    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
    public static final MapCodec<MiniGrassBlock> CODEC = simpleCodec(MiniGrassBlock::new);

    public MiniGrassBlock(Properties pProperties) {
        super(pProperties);
    }


    public MapCodec<MiniGrassBlock> codec() {
        return CODEC;
    }


    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    // Metode ini menentukan di atas blok apa saja rumput ini bisa tumbuh
    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos below = pPos.below();
        // Contoh: bisa tumbuh di atas Grass Block atau Dirt
        return this.mayPlaceOn(pLevel.getBlockState(below), pLevel, below);
    }

    // --- LOGIKA BONEMEAL ---

    /**
     * Cek apakah blok ini bisa di-bonemeal saat ini.
     * Kita buat 'true' agar selalu bisa.
     */
    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        return true;
    }

    /**
     * Cek apakah bonemeal akan berhasil (misal: ada cukup ruang).
     * Kita buat 'true' agar selalu berhasil.
     */
    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    /**
     * Aksi yang terjadi saat bonemeal berhasil.
     * Ini akan menyebarkan lebih banyak Mini Grass di sekitar.
     */
    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        // Coba sebarkan rumput di area 5x1x5 di sekitar blok yang di-bonemeal
        popResource(pLevel, pPos, new ItemStack(this)); // Jatuhkan satu sebagai item

        for (int i = 0; i < 5; ++i) {
            BlockPos randomPos = pPos.offset(pRandom.nextInt(3) - 1, pRandom.nextInt(2) - pRandom.nextInt(2), pRandom.nextInt(3) - 1);
            if (pLevel.isEmptyBlock(randomPos) && this.canSurvive(this.defaultBlockState(), pLevel, randomPos)) {
                pLevel.setBlock(randomPos, this.defaultBlockState(), 2);
            }
        }
    }
}