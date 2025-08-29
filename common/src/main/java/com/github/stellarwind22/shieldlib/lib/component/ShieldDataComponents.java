package com.github.stellarwind22.shieldlib.lib.component;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.UnaryOperator;

public class ShieldDataComponents {

    private static DeferredRegister<DataComponentType<?>> SHIELD_COMPONENTS;
    public static RegistrySupplier<DataComponentType<ShieldInformation>> SHIELD_INFORMATION;

    public static void init() {
        SHIELD_COMPONENTS = DeferredRegister.create(ShieldLib.MOD_ID, Registries.DATA_COMPONENT_TYPE);

        SHIELD_INFORMATION = registerComponent("shield_information", (builder) -> builder.persistent(
                ShieldInformation.CODEC
        ).networkSynchronized(
                ShieldInformation.STREAM_CODEC
        ));

        SHIELD_COMPONENTS.register();
    }

    private static <T> RegistrySupplier<DataComponentType<T>> registerComponent(String name, UnaryOperator<DataComponentType.Builder<T>> unaryOperator) {
        return SHIELD_COMPONENTS.register(name, () -> {
            ResourceKey.create(Registries.DATA_COMPONENT_TYPE, ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, name));
            return unaryOperator.apply(DataComponentType.builder()).build();
        });
    }
}
