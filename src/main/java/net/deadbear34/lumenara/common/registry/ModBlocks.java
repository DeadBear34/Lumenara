package net.deadbear34.lumenara.common.registry;

import net.deadbear34.lumenara.Lumenara;
import net.deadbear34.lumenara.common.block.ExplosiveBarrelBlock;
import net.deadbear34.lumenara.common.block.MiniGrassBlock;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Lumenara.MOD_ID);

    public static final DeferredBlock<Block> ADAMANTIUM_BLOCK = registerBlock("adamantium_block",
            (properties) -> new Block(properties
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));

    public static final DeferredBlock<Block> ADAMANTIUM_ORE = registerBlock("adamantium_ore",
            (properties) -> new DropExperienceBlock(UniformInt.of(2,4),
                    properties.strength(4f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> DEEPSLATE_ADAMANTIUM_ORE = registerBlock("deepslate_adamantium_ore",
            (properties) -> new DropExperienceBlock(UniformInt.of(2,4),
                    properties.strength(4f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));


    public static final DeferredBlock<Block> EXPLOSIVE_BARREL = registerBlock("explosive_barrel",
            (properties) -> new ExplosiveBarrelBlock(properties
                    .strength(2.5F).sound(SoundType.WOOD)));

    public static final DeferredBlock<Block> MINI_GRASS = registerBlock("mini_grass",
            (properties) -> new MiniGrassBlock(properties
                    .mapColor(MapColor.GRASS)
                    .noCollission() // Agar pemain bisa berjalan menembusnya
                    .instabreak()   // Langsung hancur
                    .sound(SoundType.GRASS)));


    public static final DeferredBlock<Block> RED_PETALS = registerBlock("red_petals",
            (properties) -> new FlowerBedBlock(properties
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .noOcclusion()
                    .instabreak()
                    .sound(SoundType.GRASS)));




    // Metode helper untuk mendaftarkan blok DENGAN item
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.registerItem(name, (properties) -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}