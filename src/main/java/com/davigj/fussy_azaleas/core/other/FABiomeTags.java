package com.davigj.fussy_azaleas.core.other;

import com.davigj.fussy_azaleas.core.FussyAzaleas;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class FABiomeTags {
    public static final TagKey<Biome> MOSS_GROWS_AZALEAS = biomeTag("moss_grows_azaleas");

    private static TagKey<Biome> biomeTag(String tagName) {
        return TagUtil.biomeTag(FussyAzaleas.MOD_ID, tagName);
    }
}
