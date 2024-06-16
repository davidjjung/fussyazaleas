package com.davigj.fussy_azaleas.core.data.server;

import com.davigj.fussy_azaleas.core.FussyAzaleas;
import com.davigj.fussy_azaleas.core.other.FABiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.world.level.biome.Biomes.LUSH_CAVES;

public class FABiomeTagsProvider extends BiomeTagsProvider {
    public FABiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper helper) {
        super(output, provider, FussyAzaleas.MOD_ID, helper);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(FABiomeTags.MOSS_GROWS_AZALEAS).add(LUSH_CAVES);
    }
}