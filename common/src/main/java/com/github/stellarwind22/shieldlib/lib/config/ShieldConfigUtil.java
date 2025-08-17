package com.github.stellarwind22.shieldlib.lib.config;

import com.github.stellarwind22.shieldlib.init.ShieldLib;

import java.util.Objects;
import java.util.function.Supplier;

public class ShieldConfigUtil {

    public static boolean enableTooltips() {
        Supplier<Boolean> result = Objects.requireNonNullElse(ShieldLib.enableTooltips, () -> true);
        return result.get();
    }

    public static boolean advancedTooltips() {
        Supplier<Boolean> result = Objects.requireNonNullElse(ShieldLib.enableTooltips, () -> false);
        return result.get();
    }

    public static int VanillaShieldEnchantability() {
        Supplier<Integer> result = Objects.requireNonNullElse(ShieldLib.vanillaShieldEnchantability, () -> 14);
        return result.get();
    }

    public static boolean universalDisable() {
        Supplier<Boolean> result = Objects.requireNonNullElse(ShieldLib.universalDisable, () -> false);
        return result.get();
    }
}
