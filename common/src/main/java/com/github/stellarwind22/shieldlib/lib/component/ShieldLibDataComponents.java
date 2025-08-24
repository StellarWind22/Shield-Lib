package com.github.stellarwind22.shieldlib.lib.component;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;

import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;

public class ShieldLibDataComponents {

    private static DeferredRegister<DataComponentType<?>> SHIELD_COMPONENTS;
    public static RegistrySupplier<DataComponentType<ShieldInformation>> SHIELD_INFORMATION;

    public record ShieldInformation(String type, List<String> features) {

        public static final Codec<ShieldInformation> CODEC = RecordCodecBuilder.create(
                (instance) -> instance.group(
                        ExtraCodecs.NON_EMPTY_STRING.optionalFieldOf("shield_type", null).forGetter(ShieldInformation::type),
                        ExtraCodecs.NON_EMPTY_STRING.listOf().optionalFieldOf("features", List.of("none")).forGetter(ShieldInformation::features)

                ).apply(instance, ShieldInformation::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ShieldInformation> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8, ShieldInformation::type,
                ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()), ShieldInformation::features,
                ShieldInformation::new
        );

        public boolean isType(String type) {
            return Objects.equals(this.type, type);
        }

        public boolean hasFeature(String feature) {
            for(String storedFeature : features) {
                if(storedFeature.equals(feature)) return true;
            }
            return false;
        }
    }

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
