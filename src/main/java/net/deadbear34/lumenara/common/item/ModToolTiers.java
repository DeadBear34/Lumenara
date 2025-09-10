package net.deadbear34.lumenara.common.item;

import net.deadbear34.lumenara.common.registry.ModItems;
import net.deadbear34.lumenara.common.util.ModTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {
    public static final Tier ADAMANTIUM = new SimpleTier(ModTags.Blocks.INCORRECT_ADAMANTIUM_TOOL,
            4500, 10f,4f, 15, () -> Ingredient.of(ModItems.ADAMANTIUM_INGOT));

}