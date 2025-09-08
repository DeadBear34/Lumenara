package net.deadbear34.lumenara.registry;

import net.deadbear34.lumenara.Lumenara;
import net.deadbear34.lumenara.common.items.ModToolTiers;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Lumenara.MOD_ID);

    public static final DeferredItem<Item> ADAMANTIUM_INGOT = ITEMS.register("adamantium_ingot",
            () -> new Item(new Item.Properties().fireResistant()));

    public static final DeferredItem<Item> RAW_ADAMANTIUM = ITEMS.register("raw_adamantium",
            () -> new Item(new Item.Properties().fireResistant()));

    public static final DeferredItem<Item> ADAMANTIUM_NUGGET = ITEMS.register("adamantium_nugget",
            () -> new Item(new Item.Properties().fireResistant()));

    public static final DeferredItem<SwordItem> ADAMANTIUM_SWORD = ITEMS.register("adamantium_sword",
            () -> new SwordItem(ModToolTiers.ADAMANTIUM, new Item.Properties().fireResistant()
                    .attributes(SwordItem.createAttributes(ModToolTiers.ADAMANTIUM, 7,-2.4F))));

    public static final DeferredItem<PickaxeItem> ADAMANTIUM_PICKAXE = ITEMS.register("adamantium_pickaxe",
            () -> new PickaxeItem(ModToolTiers.ADAMANTIUM, new Item.Properties().fireResistant()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.ADAMANTIUM, 2,-2.0F))));

    public static final DeferredItem<AxeItem> ADAMANTIUM_AXE = ITEMS.register("adamantium_axe",
            () -> new AxeItem(ModToolTiers.ADAMANTIUM, new Item.Properties().fireResistant()
                    .attributes(AxeItem.createAttributes(ModToolTiers.ADAMANTIUM, 6,-3.0F))));

    public static final DeferredItem<ShovelItem> ADAMANTIUM_SHOVEL = ITEMS.register("adamantium_shovel",
            () -> new ShovelItem(ModToolTiers.ADAMANTIUM, new Item.Properties().fireResistant()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.ADAMANTIUM, 2,-3.0F))));

    public static final DeferredItem<HoeItem> ADAMANTIUM_HOE = ITEMS.register("adamantium_hoe",
            () -> new HoeItem(ModToolTiers.ADAMANTIUM, new Item.Properties().fireResistant()
                    .attributes(HoeItem.createAttributes(ModToolTiers.ADAMANTIUM, -4,0.0F))));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
