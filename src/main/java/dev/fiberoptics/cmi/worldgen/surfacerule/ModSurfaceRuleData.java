package dev.fiberoptics.cmi.worldgen.surfacerule;

import dev.fiberoptics.cmi.CMI;
import dev.fiberoptics.cmi.worldgen.biome.ModBiomes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class ModSurfaceRuleData {

    private static final SurfaceRules.RuleSource ANDESITE = makeStateRule(Blocks.ANDESITE);
    private static final SurfaceRules.RuleSource STONE = makeStateRule(Blocks.STONE);
    private static final SurfaceRules.RuleSource PEAT = makeStateRule(
            BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(CMI.MODID, "peat_block")));

    public static SurfaceRules.RuleSource makeRules() {
        return SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.ANDESITE_CAVE), ANDESITE));
    }

    public static SurfaceRules.RuleSource makeInjections() {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.MANGROVE_SWAMP),
                                                SurfaceRules.sequence(
                                                        SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(60),0)),PEAT),
                                                        SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63),0),PEAT),
                                                        SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.noiseCondition(Noises.SWAMP,0)), PEAT)
                                                )
                                        ),
                                        SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.SWAMP), PEAT)
                                )
                        )

                ),
                SurfaceRules.ifTrue(SurfaceRules.waterStartCheck(-6,-1),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
                                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.MANGROVE_SWAMP,Biomes.SWAMP), PEAT)
                        )
                )
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
