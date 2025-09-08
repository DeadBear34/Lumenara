package net.deadbear34.lumenara.common.items;

import net.deadbear34.lumenara.registry.ModItems;
import net.deadbear34.lumenara.util.ModTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {
    public static final Tier ADAMANTIUM = new SimpleTier(ModTags.Blocks.INCORRECT_ADAMANTIUM_TOOL,
            4000, 10f,4f, 15, () -> Ingredient.of(ModItems.ADAMANTIUM_INGOT));

}
