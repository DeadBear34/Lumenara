package net.deadbear34.lumenara.common.worldgen;

import net.deadbear34.lumenara.Lumenara;
import net.deadbear34.lumenara.common.registry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

import static net.minecraft.data.worldgen.features.VegetationFeatures.SINGLE_PIECE_OF_GRASS;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_ADAMANTIUM_ORE_KEY = registerKey("adamantium_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> MINI_GRASS_KEY = registerKey("mini_grass");

    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_PETALS_KEY = registerKey("red_petals");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> overworldAdamantiumOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.ADAMANTIUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_ADAMANTIUM_ORE.get().defaultBlockState()));

        register(context, OVERWORLD_ADAMANTIUM_ORE_KEY, Feature.ORE, new OreConfiguration(overworldAdamantiumOres, 2));

        register(context,
                MINI_GRASS_KEY,
                Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.MINI_GRASS.get().defaultBlockState())),
                        List.of(),
                        32
                ));

        register(context,
                RED_PETALS_KEY,
                Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(
                        Feature.FLOWER,
                        new RandomPatchConfiguration(
                                96,
                                6,
                                2,
                                PlacementUtils.onlyWhenEmpty(
                                        Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(flowerBedPatchBuilder(Blocks.PINK_PETALS)))
                                )
                        ),
                        List.of(),
                        32
                ));


    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Lumenara.MOD_ID, name));
    }

    private static WeightedList.Builder<BlockState> flowerBedPatchBuilder(Block block) {
        return segmentedBlockPatchBuilder(block, 1, 4, FlowerBedBlock.AMOUNT, FlowerBedBlock.FACING);
    }

    private static WeightedList.Builder<BlockState> segmentedBlockPatchBuilder(
            Block block, int minAmount, int maxAmount, IntegerProperty amountProperty, EnumProperty<Direction> directionProperty
    ) {
        WeightedList.Builder<BlockState> builder = WeightedList.builder();

        for (int i = minAmount; i <= maxAmount; i++) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                builder.add(block.defaultBlockState().setValue(amountProperty, i).setValue(directionProperty, direction), 1);
            }
        }

        return builder;
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
