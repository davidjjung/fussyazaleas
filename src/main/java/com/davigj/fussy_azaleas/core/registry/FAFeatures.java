package com.davigj.fussy_azaleas.core.registry;

import com.davigj.fussy_azaleas.core.FussyAzaleas;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = FussyAzaleas.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FAFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, FussyAzaleas.MOD_ID);

    public static final RegistryObject<Feature<SimpleBlockConfiguration>> FUSSY_MOSS_VEGETATION = FEATURES.register("fussy_moss_vegetation", () -> new SimpleBlockFeature(SimpleBlockConfiguration.CODEC));
    public static final RegistryObject<Feature<SimpleBlockConfiguration>> FUSSY_MOSS_PATCH_BONEMEAL = FEATURES.register("fussy_moss_patch_bonemeal", () -> new SimpleBlockFeature(SimpleBlockConfiguration.CODEC));

    public static final class FAConfiguredFeatures {
        public static final ResourceKey<ConfiguredFeature<?, ?>> FUSSY_MOSS_PATCH_BONEMEAL = createKey("fussy_moss_patch_bonemeal");
        public static final ResourceKey<ConfiguredFeature<?, ?>> FUSSY_MOSS_VEGETATION = createKey("fussy_moss_vegetation");

        public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
            register(context, FUSSY_MOSS_PATCH_BONEMEAL, Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.MOSS_REPLACEABLE,
                    BlockStateProvider.simple(Blocks.MOSS_BLOCK), PlacementUtils.inlinePlaced(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(FUSSY_MOSS_VEGETATION)),
                    CaveSurface.FLOOR, ConstantInt.of(1), 0.0F, 5, 0.6F, UniformInt.of(1, 2), 0.75F));

            register(context, FUSSY_MOSS_VEGETATION, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                    new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                            .add(Blocks.MOSS_CARPET.defaultBlockState(),  25).add(Blocks.GRASS.defaultBlockState(), 50)
                            .add(Blocks.TALL_GRASS.defaultBlockState(), 10))));
        }

        public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
            return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(FussyAzaleas.MOD_ID, name));
        }

        public static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC config) {
            context.register(key, new ConfiguredFeature<>(feature, config));
        }
    }
}
