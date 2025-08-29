package com.github.stellarwind22.shieldlib.lib.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

import java.util.List;

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
        return this.type.equals(type);
    }

    public boolean hasFeature(String feature) {
        for(String storedFeature : features) {
            if(storedFeature.equals(feature)) return true;
        }
        return false;
    }

    public boolean hasFeatures(List<String> features) {
        for(String feature : features) {
            if(!hasFeature(feature)) return false;
        }
        return true;
    }
}
