package net.deadbear34.lumenara.common.datagen;

import net.deadbear34.lumenara.Lumenara;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Lumenara.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Tambahkan tag item di sini
    }
}
