package net.deadbear34.lumenara.common.registry;

import net.deadbear34.lumenara.Lumenara;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Lumenara.MOD_ID);

    public static final Supplier<CreativeModeTab> LUMENARA_CREATIVE_TAB = CREATIVE_MODE_TAB.register("lumenara_creative_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ADAMANTIUM_INGOT.get()))
                    .title(Component.translatable("creativetab.lumenara.lumenara_creative_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.RAW_ADAMANTIUM);
                        output.accept(ModItems.ADAMANTIUM_INGOT);
                        output.accept(ModItems.ADAMANTIUM_NUGGET);

                        output.accept(ModBlocks.ADAMANTIUM_ORE);
                        output.accept(ModBlocks.DEEPSLATE_ADAMANTIUM_ORE);
                        output.accept(ModBlocks.ADAMANTIUM_BLOCK);

                        output.accept(ModItems.ADAMANTIUM_SWORD);
                        output.accept(ModItems.ADAMANTIUM_SHOVEL);
                        output.accept(ModItems.ADAMANTIUM_PICKAXE);
                        output.accept(ModItems.ADAMANTIUM_AXE);
                        output.accept(ModItems.ADAMANTIUM_HOE);

                        output.accept(ModBlocks.EXPLOSIVE_BARREL);

                        output.accept(ModItems.NAUTILUS_SPAWN_EGG);

                        // TAMBAHKAN BLOK BARU ANDA DI SINI
                        output.accept(ModBlocks.MINI_GRASS);
                        output.accept(ModBlocks.RED_PETALS);



                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
