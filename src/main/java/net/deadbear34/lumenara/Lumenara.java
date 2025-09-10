package net.deadbear34.lumenara;


import net.deadbear34.lumenara.common.entity.client.NautilusRenderer;
import net.deadbear34.lumenara.common.loot.ModLootModifiers;
import net.deadbear34.lumenara.client.particle.ModParticles;
import net.deadbear34.lumenara.client.particle.TemplateParticles;
import net.deadbear34.lumenara.common.registry.*;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
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


@Mod(Lumenara.MOD_ID)
public class Lumenara {

    public static final String MOD_ID = "lumenara";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Lumenara(IEventBus modEventBus, ModContainer modContainer) {

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        NeoForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);

        //Registries
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModParticles.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModEntities.register(modEventBus);
        ModLootModifiers.register(modEventBus);


        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our Config class to the event bus
        modEventBus.register(Config.class);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Kode setup umum Anda di sini
    }

    private void addCreative(final BuildCreativeModeTabContentsEvent event) {

    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.NAUTILUS.get(), NautilusRenderer::new);
        }
        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(ModParticles.TEMPLATE_PARTICLES.get(),
                    (spriteSet) -> new TemplateParticles.Provider(spriteSet));

            event.registerSpriteSet(ModParticles.SOUL_FIRE_PARTICLE.get(),
                    (spriteSet) -> new TemplateParticles.Provider(spriteSet));

            event.registerSpriteSet(ModParticles.ROCKET_BURST_PARTICLE.get(),
                    (spriteSet) -> new TemplateParticles.Provider(spriteSet));

            event.registerSpriteSet(ModParticles.EXPLOSION_DEBRIS_PARTICLE.get(),
                    (spriteSet) -> new TemplateParticles.Provider(spriteSet));
        }

    }
}