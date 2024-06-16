package com.davigj.fussy_azaleas.core;

import com.davigj.fussy_azaleas.core.data.server.FABiomeTagsProvider;
import com.davigj.fussy_azaleas.core.data.server.FADatapackBuiltinEntriesProvider;
import com.davigj.fussy_azaleas.core.registry.FAFeatures;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.concurrent.CompletableFuture;

@Mod(FussyAzaleas.MOD_ID)
public class FussyAzaleas {
    public static final String MOD_ID = "fussy_azaleas";
    public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

    public FussyAzaleas() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

		REGISTRY_HELPER.register(bus);
        FAFeatures.FEATURES.register(bus);

        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);
        bus.addListener(this::dataSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {

        });
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {

        });
    }

    private void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        boolean server = event.includeServer();
        FADatapackBuiltinEntriesProvider datapackEntries = new FADatapackBuiltinEntriesProvider(output, provider);
        generator.addProvider(server, datapackEntries);

        provider = datapackEntries.getRegistryProvider();
        generator.addProvider(server, new FABiomeTagsProvider(output, provider, helper));
    }
}