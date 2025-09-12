package com.github.stellarwind22.shieldlib.lib.object;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;

public class ShieldLibDamage {

    public static final ResourceKey<DamageType> HIT_SPIKED_SHIELD = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "hit_spiked_shield"));
    public static final ResourceKey<DamageType> COLLIDE_SPIKED_SHIELD = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "hit_spiked_shield"));

    public static Holder<DamageType> of(RegistryAccess access, ResourceKey<DamageType> key) {
        return access.lookup(Registries.DAMAGE_TYPE).orElseThrow().getOrThrow(key);
    }

    @SuppressWarnings("unused")
    public static DamageSource sourceOf(RegistryAccess access, ResourceKey<DamageType> key) {
        return new DamageSource(access.lookup(Registries.DAMAGE_TYPE).orElseThrow().getOrThrow(key));
    }

    @SuppressWarnings("unused")
    public static DamageSource sourceOf(RegistryAccess access, ResourceKey<DamageType> key, Entity receiver) {
        return new DamageSource(of(access, key), receiver);
    }

    public static DamageSource sourceOf(RegistryAccess access, ResourceKey<DamageType> key, Entity dealer, Entity receiver) {
        return new DamageSource(of(access, key), dealer, receiver);
    }
}
