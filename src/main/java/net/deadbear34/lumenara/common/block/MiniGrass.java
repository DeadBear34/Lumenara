package net.deadbear34.lumenara.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.IShearable;

public class MiniGrass extends BushBlock implements BonemealableBlock, IShearable {

    public static final MapCodec<MiniGrass> CODEC = simpleCodec(MiniGrass::new);

    protected static final VoxelShape SHAPE = Block.box(
            2.0D, 0.0D, 2.0D,
            14.0D, 8.0D, 14.0D
    );

    public MiniGrass(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<MiniGrass> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }


    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {

        BlockPos newPos = pos.offset(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
        if (level.isEmptyBlock(newPos) && state.canSurvive(level, newPos)) {
            level.setBlock(newPos, this.defaultBlockState(), 2);
        }
    }
}
