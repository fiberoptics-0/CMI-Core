package dev.fiberoptics.cmi.mixin;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(BlazeBurnerBlock.HeatLevel.class)
public abstract class HeatLevelMixin {

    @Shadow
    @Final
    @Mutable
    private static BlazeBurnerBlock.HeatLevel[] $VALUES;

    @Unique
    private static final BlazeBurnerBlock.HeatLevel cmi$PLAIN = cmi$addVariant("GRILLED");

    @Invoker("<init>")
    public static BlazeBurnerBlock.HeatLevel cmi$invokeInit(String name, int id) {
        throw new AssertionError();
    }

    @Unique
    private static BlazeBurnerBlock.HeatLevel cmi$addVariant(String name) {
        ArrayList<BlazeBurnerBlock.HeatLevel> variants = new ArrayList<>(Arrays.asList($VALUES));
        BlazeBurnerBlock.HeatLevel heatLevel = cmi$invokeInit(name, variants.get(variants.size() - 1).ordinal() + 1);
        variants.add(heatLevel);
        $VALUES = variants.toArray(new BlazeBurnerBlock.HeatLevel[0]);
        return heatLevel;
    }

}
