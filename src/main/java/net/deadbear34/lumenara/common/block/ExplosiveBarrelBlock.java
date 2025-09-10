package net.deadbear34.lumenara.common.block;

import com.mojang.serialization.MapCodec;
import java.util.function.BiConsumer;
import net.deadbear34.lumenara.common.block.entity.ExplosiveBarrelBlockEntity;
import net.deadbear34.lumenara.client.particle.system.LumenaraParticles;
import net.deadbear34.lumenara.common.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class ExplosiveBarrelBlock extends BaseEntityBlock {

    public static final BooleanProperty PRIMED = BlockStateProperties.LIT;
    public static final MapCodec<ExplosiveBarrelBlock> CODEC = simpleCodec(ExplosiveBarrelBlock::new);

    public ExplosiveBarrelBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PRIMED, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(PRIMED);
    }

    /**
     * Memunculkan partikel sumbu di sisi klien saat barrel menyala.
     */
    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(PRIMED)) {
            // Lidah api (rocketBurst) ke atas
            for (int i = 0; i < 5; i++) {
                double topX = pPos.getX() + 0.5 + (pRandom.nextDouble() - 0.5) * 0.1; // Sedikit variasi
                double topY = pPos.getY() + 1.05;
                double topZ = pPos.getZ() + 0.5 + (pRandom.nextDouble() - 0.5) * 0.1;

                // Fokus ke atas, sedikit variasi X/Z
                double xSpeed = (pRandom.nextDouble() - 0.5) * 0.02;
                double ySpeed = 0.3 + pRandom.nextDouble() * 0.2; // Selalu naik
                double zSpeed = (pRandom.nextDouble() - 0.5) * 0.02;

                pLevel.addParticle(LumenaraParticles.rocketBurst(), topX, topY, topZ, xSpeed, ySpeed, zSpeed);
            }

            // Percikan kecil (kayak sumbu terbakar)
            if (pRandom.nextFloat() < 0.3f) {
                double sparkX = pPos.getX() + 0.5;
                double sparkY = pPos.getY() + 1.0;
                double sparkZ = pPos.getZ() + 0.5;
                pLevel.addParticle(LumenaraParticles.simple(new Vector3f(1.0f, 0.9f, 0.5f), 5),
                        sparkX, sparkY, sparkZ, 0, 0.05, 0);
            }
        }
    }


    /**
     * Menangani interaksi saat pemain menyalakan barrel dengan Flint and Steel.
     */
    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pState.getValue(PRIMED) && pStack.is(Items.FLINT_AND_STEEL)) {
            prime(pLevel, pPos, 80); // Sumbu panjang (4 detik)
            pStack.hurtAndBreak(1, pPlayer, Player.getSlotForHand(pHand));
            return ItemInteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return super.useItemOn(pStack, pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    /**
     * Menangani pemicu saat barrel terkena ledakan lain.
     */
    @Override
    public void onExplosionHit(BlockState state, Level level, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> dropConsumer) {
        if (!state.getValue(PRIMED)) {
            prime(level, pos, 10); // Sumbu pendek (0.5 detik)
        }
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        // Cek apakah barrel belum menyala dan menerima sinyal redstone yang kuat
        if (!pState.getValue(PRIMED) && pLevel.hasNeighborSignal(pPos)) {
            prime(pLevel, pPos, 80); // Nyalakan dengan sumbu normal (4 detik)
        }
    }

    /**
     * Metode helper untuk menyalakan barrel dengan durasi sumbu tertentu.
     */
    public static void prime(Level level, BlockPos pos, int fuseTicks) {
        if (level.isClientSide) return;

        level.setBlock(pos, level.getBlockState(pos).setValue(PRIMED, true), 3);

        if (level.getBlockEntity(pos) instanceof ExplosiveBarrelBlockEntity barrelEntity) {
            barrelEntity.primeFuse(fuseTicks);
        }

        level.playSound(null, pos, SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ExplosiveBarrelBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pState.getValue(PRIMED)) {
            return createTickerHelper(pBlockEntityType, ModBlockEntities.EXPLOSIVE_BARREL_ENTITY.get(),
                    (level, pos, state, be) -> be.tick(level, pos, state));
        }
        return null;
    }


    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
}