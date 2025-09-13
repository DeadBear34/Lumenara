package net.deadbear34.lumenara.common.registry;

import net.deadbear34.lumenara.Lumenara;
import net.deadbear34.lumenara.common.item.ModToolTiers;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Lumenara.MOD_ID);

    public static final DeferredItem<Item> ADAMANTIUM_INGOT = ITEMS.registerItem("adamantium_ingot", Item::new, new Item.Properties().fireResistant());

    public static final DeferredItem<Item> RAW_ADAMANTIUM = ITEMS.registerItem("raw_adamantium", Item::new, new Item.Properties().fireResistant());

    public static final DeferredItem<Item> ADAMANTIUM_NUGGET = ITEMS.registerItem("adamantium_nugget",Item::new, new Item.Properties().fireResistant());

    public static final DeferredItem<Item> ADAMANTIUM_SWORD = ITEMS.registerItem("adamantium_sword",
            (properties) -> new Item(properties.sword(ModToolTiers.ADAMANTIUM,7,-2.4F)));

    public static final DeferredItem<Item> ADAMANTIUM_PICKAXE = ITEMS.registerItem("adamantium_pickaxe",
            (properties) -> new Item(properties.pickaxe(ModToolTiers.ADAMANTIUM, 2,-2.0F)));

    public static final DeferredItem<Item> ADAMANTIUM_AXE = ITEMS.registerItem("adamantium_axe",
            (properties) -> new Item(properties.axe(ModToolTiers.ADAMANTIUM, 6,-3.0F)));

    public static final DeferredItem<Item> ADAMANTIUM_SHOVEL = ITEMS.registerItem("adamantium_shovel",
            (properties) -> new Item(properties.shovel(ModToolTiers.ADAMANTIUM, 2,-3.0F)));

    public static final DeferredItem<Item> ADAMANTIUM_HOE = ITEMS.registerItem("adamantium_hoe",
            (properties) -> new Item(properties.hoe(ModToolTiers.ADAMANTIUM, -4,0.0F)));

    public static final DeferredItem<Item> NAUTILUS_SPAWN_EGG = ITEMS.registerItem("nautilus_spawn_egg",
            (properties) -> new SpawnEggItem(ModEntities.NAUTILUS.get(),
                    properties));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
