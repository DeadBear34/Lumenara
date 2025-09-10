package net.deadbear34.lumenara.worldgen;

import net.deadbear34.lumenara.Lumenara;
import net.deadbear34.lumenara.registry.ModEntities;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_ADAMANTIUM_ORE = registerKey("add_adamantium_ore");

    public static final ResourceKey<BiomeModifier> SPAWN_NAUTILUS = registerKey("spawn_nautilus");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        // CF -> PF -> BM
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_ADAMANTIUM_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ADAMANTIUM_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        // Example for individual Biomes!
        // context.register(ADD_BISMUTH_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
        //         HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS), biomes.getOrThrow(Biomes.SAVANNA)),
        //         HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.BISMUTH_ORE_PLACED_KEY)),
        //         GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(SPAWN_NAUTILUS, new BiomeModifiers.AddSpawnsBiomeModifier(
                // Targetkan semua biome yang memiliki tag "IS_OCEAN"
                biomes.getOrThrow(BiomeTags.IS_OCEAN),
                List.of(new MobSpawnSettings.SpawnerData(
                        ModEntities.NAUTILUS.get(),
                        15, // Bobot (weight): seberapa besar peluang spawn. Makin tinggi, makin sering.
                        2,  // Jumlah min: jumlah minimum dalam satu grup.
                        5   // Jumlah max: jumlah maksimum dalam satu grup.
                ))
        ));

    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(Lumenara.MOD_ID, name));
    }
}
