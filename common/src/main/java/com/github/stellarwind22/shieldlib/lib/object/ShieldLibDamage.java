package com.github.stellarwind22.shieldlib.lib.object;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

public class ShieldLibDamage {

    public static final ResourceKey<DamageType> HIT_SPIKED_SHIELD = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "hit_spiked_shield"));
    public static final ResourceKey<DamageType> COLLIDE_SPIKED_SHIELD = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "hit_spiked_shield"));

    public static Holder<DamageType> of(ServerLevel level, ResourceKey<DamageType> key) {
        RegistryAccess access = level.registryAccess();
        return access.lookup(Registries.DAMAGE_TYPE).orElseThrow().getOrThrow(key);
    }

    public static DamageSource sourceOf(ServerLevel level, ResourceKey<DamageType> key) {
        RegistryAccess access = level.registryAccess();
        return new DamageSource(access.lookup(Registries.DAMAGE_TYPE).orElseThrow().getOrThrow(key));
    }

    public static Holder<DamageSource> sourceHolderOf(ServerLevel level, ResourceKey<DamageType> key) {
        return new Holder.Direct<>(sourceOf(level, key));
    }
}
