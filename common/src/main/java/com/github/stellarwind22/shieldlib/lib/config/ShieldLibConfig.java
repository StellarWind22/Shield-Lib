package com.github.stellarwind22.shieldlib.lib.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class ShieldLibConfig extends MidnightConfig {

    @Entry
    public static ToolTipMode cooldown_tooltip_mode = ToolTipMode.NORMAL;
    public enum ToolTipMode {
        DISABLED, NORMAL, ADVANCED
    }

    @Comment(url = "enchantability_convention")
    public static Comment enchantability_convention;

    @Entry
    public static  int vanilla_shield_enchantability = 14;

    @Entry
    public static boolean universal_disabling = true;

    @Comment(url = "universal_disabling_description_1")
    public static Comment universal_disabling_description_1;

    @Comment(url = "universal_disabling_description_2")
    public static Comment universal_disabling_description_2;
}
