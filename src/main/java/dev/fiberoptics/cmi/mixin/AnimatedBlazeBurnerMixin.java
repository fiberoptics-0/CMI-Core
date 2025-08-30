package dev.fiberoptics.cmi.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.compat.jei.category.animations.AnimatedBlazeBurner;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import com.simibubi.create.foundation.utility.Lang;
import dev.fiberoptics.cmi.util.ModLang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnimatedBlazeBurner.class)
public abstract class AnimatedBlazeBurnerMixin extends AnimatedKinetics {
    @Shadow
    private BlazeBurnerBlock.HeatLevel heatLevel;

    @Inject(method = "draw", at = @At("HEAD"), remap = false, cancellable = true)
    public void draw(GuiGraphics graphics, int xOffset, int yOffset, CallbackInfo ci) {
        if(heatLevel == BlazeBurnerBlock.HeatLevel.valueOf("GRILLED")) {
            PoseStack matrixStack = graphics.pose();
            matrixStack.pushPose();
            matrixStack.translate((float)xOffset, (float)yOffset, 200.0F);
            matrixStack.mulPose(Axis.XP.rotationDegrees(-15.5F));
            matrixStack.mulPose(Axis.YP.rotationDegrees(22.5F));
            int scale = 23;
            blockElement(Blocks.FIRE.defaultBlockState()).atLocal((double)0.0F, 1.65, (double)0.0F).scale((double)scale).render(graphics);
            matrixStack.popPose();
            ci.cancel();
        }
    }
}
