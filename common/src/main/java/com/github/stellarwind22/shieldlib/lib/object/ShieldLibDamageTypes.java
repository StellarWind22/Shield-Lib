package com.github.stellarwind22.shieldlib.lib.object;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

import java.util.function.Supplier;

public class ShieldLibDamageTypes {

    public static DeferredRegister<DamageType> SHIELDLIB_DAMAGE_TYPES;

    public static RegistrySupplier<DamageType> HIT_SPIKED_SHIELD;
    public static RegistrySupplier<DamageType> COLLIDE_SPIKED_SHIELD;

    public static void init() {
        SHIELDLIB_DAMAGE_TYPES = DeferredRegister.create(ShieldLib.MOD_ID, Registries.DAMAGE_TYPE);

        HIT_SPIKED_SHIELD = register("hit_spiked_shield", () -> new DamageType("hit_spiked_shield", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.1F));
        COLLIDE_SPIKED_SHIELD = register("collide_spiked_shield", () -> new DamageType("collide_spiked_shield", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.1F));

        SHIELDLIB_DAMAGE_TYPES.register();
    }

    private static <T extends DamageType> RegistrySupplier<T> register(String name, Supplier<T> damageType) {
        return SHIELDLIB_DAMAGE_TYPES.register(name, damageType);
    }
}
