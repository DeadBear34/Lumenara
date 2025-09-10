package net.deadbear34.lumenara.common.util;


import net.deadbear34.lumenara.Lumenara;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {

        public static final TagKey<Block> NEEDS_ADAMANTIUM_TOOL = createTag("needs_adamantium_tool");
        public static final TagKey<Block> INCORRECT_ADAMANTIUM_TOOL = createTag("incorrect_adamantium_tool");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(Lumenara.MOD_ID, name));
        }
    }

    public static class Items {

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(Lumenara.MOD_ID, name));
        }
    }
}
