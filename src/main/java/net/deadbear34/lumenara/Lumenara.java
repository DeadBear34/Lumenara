package net.deadbear34.lumenara;


import net.deadbear34.lumenara.registry.ModBlocks;
import net.deadbear34.lumenara.registry.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;


// package dan import Anda...

@Mod(Lumenara.MOD_ID)
public class Lumenara {

    public static final String MOD_ID = "lumenara";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Lumenara(IEventBus modEventBus, ModContainer modContainer) {

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        NeoForge.EVENT_BUS.register(this);

        //Registries
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our Config class to the event bus
        modEventBus.register(Config.class);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Kode setup umum Anda di sini
    }

    private void addCreative(final BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.ADAMANTIUM_INGOT);
            event.accept(ModItems.RAW_ADAMANTIUM);
            event.accept(ModItems.ADAMANTIUM_NUGGET);
        }

        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.ADAMANTIUM_BLOCK);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {


    }

    private void onClientSetup(final FMLClientSetupEvent event) {

    }
}