package com.github.stellarwind22.shieldlib.lib.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class ShieldLibConfig extends MidnightConfig {

    @Entry
    public static tooltip_mode cooldown_tooltip_mode = tooltip_mode.NORMAL;

    @Entry
    public static tooltip_mode movement_tooltip_mode = tooltip_mode.DISABLED;

    @Comment
    public static Comment enchantability_convention;

    @Entry
    public static  int vanilla_shield_enchantability = 14;

    @Entry
    public static boolean universal_disabling = true;

    @Comment
    public static Comment universal_disabling_description_1;

    @Comment
    public static Comment universal_disabling_description_2;

    public enum tooltip_mode {
        DISABLED, NORMAL, COMPACT
    }
}
