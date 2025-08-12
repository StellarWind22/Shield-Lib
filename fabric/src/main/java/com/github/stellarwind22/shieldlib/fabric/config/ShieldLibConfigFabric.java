package com.github.stellarwind22.shieldlib.fabric.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class ShieldLibConfigFabric extends MidnightConfig {

    @Entry
    public static boolean enable_tooltips = true;

    @Comment
    public static Comment enchantability_convention;

    @Entry
    public static int vanilla_shield_enchantability = 14;

    @Entry
    public static boolean universal_disable = false;

    @Comment
    public static Comment universal_disable_description_1;

    @Comment
    public static Comment universal_disable_description_2;
}
