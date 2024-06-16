package com.davigj.fussy_azaleas.core.mixin;

import com.davigj.fussy_azaleas.core.other.FABiomeTags;
import com.davigj.fussy_azaleas.core.registry.FAFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.MossBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MossBlock.class)
public class MossBlockMixin {
    @Inject(method = "performBonemeal", at = @At("HEAD"), cancellable = true)
    public void onBoneMeal(ServerLevel server, RandomSource random, BlockPos pos, BlockState state, CallbackInfo ci){
        boolean isAzaleaFriendly = false;
        for (Direction direction : Direction.values()) {
            BlockPos candPos = pos.relative(direction);
            Holder<Biome> holder = server.getBiome(candPos);
            if (holder.is(FABiomeTags.MOSS_GROWS_AZALEAS)) {
                isAzaleaFriendly = true;
            }
        }
        if (!isAzaleaFriendly) {
            server.registryAccess().registry(Registries.CONFIGURED_FEATURE).flatMap((p_258973_) -> {
                return p_258973_.getHolder(FAFeatures.FAConfiguredFeatures.FUSSY_MOSS_PATCH_BONEMEAL);
            }).ifPresent((p_255669_) -> {
                p_255669_.value().place(server, server.getChunkSource().getGenerator(), random, pos.above());
            });
            ci.cancel();
        }
    }
}
