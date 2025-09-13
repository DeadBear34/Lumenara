package net.deadbear34.lumenara.common.worldgen;

import net.deadbear34.lumenara.Lumenara;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.FlowerBedBlock;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> ADAMANTIUM_ORE_PLACED_KEY = registerKey("adamantium_ore_placed");
    public static final ResourceKey<PlacedFeature> MINI_GRASS_PLACED_KEY = registerKey("mini_grass_placed");

    public static final ResourceKey<PlacedFeature> RED_PETALS_PLACED_KEY = registerKey("red_petals_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, ADAMANTIUM_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_ADAMANTIUM_ORE_KEY),
                ModOrePlacement.rareOrePlacement(40,
                        HeightRangePlacement.uniform(VerticalAnchor.bottom(),
                                VerticalAnchor.absolute(-48))));

        register(context,
                MINI_GRASS_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.MINI_GRASS_KEY),
                VegetationPlacements.worldSurfaceSquaredWithCount(4));


        register(context,
                RED_PETALS_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.RED_PETALS_KEY),
                VegetationPlacements.worldSurfaceSquaredWithCount(5) // lebih sering daripada mini_grass
        );
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Lumenara.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}