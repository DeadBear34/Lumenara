package net.deadbear34.lumenara.common.datagen;

import net.deadbear34.lumenara.Lumenara;
import net.deadbear34.lumenara.common.registry.ModBlocks;
import net.deadbear34.lumenara.common.registry.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;


import java.util.LinkedHashMap;
import java.util.stream.Stream;

public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, Lumenara.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        itemModels.generateFlatItem(ModItems.RAW_ADAMANTIUM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ADAMANTIUM_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ADAMANTIUM_NUGGET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.NAUTILUS_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.ADAMANTIUM_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.ADAMANTIUM_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.ADAMANTIUM_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.ADAMANTIUM_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.ADAMANTIUM_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);


        blockModels.createTrivialCube(ModBlocks.ADAMANTIUM_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.DEEPSLATE_ADAMANTIUM_ORE.get());
        blockModels.createTrivialCube(ModBlocks.ADAMANTIUM_ORE.get());

        blockModels.createCrossBlock(ModBlocks.MINI_GRASS.get(),BlockModelGenerators.PlantType.TINTED);
        blockModels.createFlowerBed(ModBlocks.RED_PETALS.get());



        ResourceLocation barrelOffModel = ModelTemplates.CUBE_BOTTOM_TOP.create(
                modBlock("explosive_barrel"),
                TextureMapping.cubeBottomTop(ModBlocks.EXPLOSIVE_BARREL.get())
                        .put(TextureSlot.SIDE,   modBlock("explosive_barrel_side"))
                        .put(TextureSlot.TOP,    modBlock("explosive_barrel_top"))
                        .put(TextureSlot.BOTTOM, modBlock("explosive_barrel_bottom")),
                blockModels.modelOutput
        );


        ResourceLocation barrelOnModel = ModelTemplates.CUBE_BOTTOM_TOP.create(
                modBlock("explosive_barrel_primed"),
                TextureMapping.cubeBottomTop(ModBlocks.EXPLOSIVE_BARREL.get())
                        .put(TextureSlot.SIDE,   modBlock("explosive_barrel_side_primed"))
                        .put(TextureSlot.TOP,    modBlock("explosive_barrel_top"))
                        .put(TextureSlot.BOTTOM, modBlock("explosive_barrel_bottom")),
                blockModels.modelOutput
        );


        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(ModBlocks.EXPLOSIVE_BARREL.get())
                        .with(BlockModelGenerators.createBooleanModelDispatch(
                                BlockStateProperties.LIT,
                                BlockModelGenerators.plainVariant(barrelOnModel),   // true  -> ON
                                BlockModelGenerators.plainVariant(barrelOffModel)   // false -> OFF
                        ))
        );


        blockModels.registerSimpleItemModel(ModBlocks.EXPLOSIVE_BARREL.get(), barrelOffModel);

    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream();
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return ModItems.ITEMS.getEntries().stream();
    }


    private static ResourceLocation modBlock(String path) {
        return ResourceLocation.fromNamespaceAndPath(Lumenara.MOD_ID, "block/" + path);
    }

}