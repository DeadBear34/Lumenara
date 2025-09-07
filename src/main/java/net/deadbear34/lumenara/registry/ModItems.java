package net.deadbear34.lumenara.registry;

import net.deadbear34.lumenara.Lumenara;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Lumenara.MOD_ID);

    public static final DeferredItem<Item> ADAMANTIUM_INGOT = ITEMS.register("adamantium_ingot",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_ADAMANTIUM = ITEMS.register("raw_adamantium",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> ADAMANTIUM_NUGGET = ITEMS.register("adamantium_nugget",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
