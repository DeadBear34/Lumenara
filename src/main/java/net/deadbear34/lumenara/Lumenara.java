package net.deadbear34.lumenara;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.deadbear34.lumenara.particle.ModParticles;
import net.deadbear34.lumenara.particle.TemplateParticles;
import net.deadbear34.lumenara.particle.options.LumenaraParticleOptions;
import net.deadbear34.lumenara.particle.system.LumenaraParticles;
import net.deadbear34.lumenara.registry.ModBlockEntities;
import net.deadbear34.lumenara.registry.ModBlocks;
import net.deadbear34.lumenara.registry.ModCreativeModeTabs;
import net.deadbear34.lumenara.registry.ModItems;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;
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
        CommandDispatcher<CommandSourceStack> dispatcher = event.getServer().getCommands().getDispatcher();

        dispatcher.register(Commands.literal("lumenara")
                .then(Commands.literal("test")
                        // Argumen sekarang berupa nama efek (string)
                        .then(Commands.argument("effect", StringArgumentType.word())
                                .executes(context -> {
                                    String effectName = StringArgumentType.getString(context, "effect");
                                    ServerLevel level = context.getSource().getLevel();
                                    double x = context.getSource().getPosition().x;
                                    double y = context.getSource().getPosition().y;
                                    double z = context.getSource().getPosition().z;

                                    // Pilih preset partikel berdasarkan nama efek
                                    LumenaraParticleOptions options;
                                    switch (effectName.toLowerCase()) {
                                        case "soulfire":
                                            options = LumenaraParticles.soulFire();
                                            break;
                                        case "rocket":
                                            options = LumenaraParticles.rocketBurst();
                                            break;
                                        default:
                                            // Jika nama tidak dikenali, gunakan partikel putih sederhana
                                            options = LumenaraParticles.simple(new org.joml.Vector3f(1,1,1), 20);
                                            break;
                                    }

                                    level.sendParticles(options, x, y + 1.5, z, 30,   0.03, 0.03, 0.03, 0.0);
                                    return 1;
                                })
                        )
                )
        );
    }

    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(ModParticles.TEMPLATE_PARTICLES.get(),
                    (spriteSet) -> new TemplateParticles.Provider(spriteSet));

        }
    }
}