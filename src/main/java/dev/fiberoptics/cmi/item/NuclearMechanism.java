package dev.fiberoptics.cmi.item;

import mekanism.api.radiation.capability.IRadiationShielding;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class NuclearMechanism extends Item {

    public NuclearMechanism() {
        super(new Properties());
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ICapabilityProvider() {
            @Override
            public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
                if(capability == CapabilityManager.get(new CapabilityToken<ICurio>() {})) {
                    return LazyOptional.of(() -> new ICurio() {
                        @Override
                        public ItemStack getStack() {
                            return stack;
                        }
                    }).cast();
                }
                if (capability == CapabilityManager.get(new CapabilityToken<IRadiationShielding>() {})) {
                    return LazyOptional.of(() -> new IRadiationShielding() {
                        @Override
                        public double getRadiationShielding() {
                            return 1.0;
                        }
                    }).cast();
                }
                return LazyOptional.empty();
            }
        };
    }
}
