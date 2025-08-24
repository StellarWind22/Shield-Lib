package com.github.stellarwind22.shieldlib.lib.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class ShieldLibConfig extends MidnightConfig {

    public static final String MAIN = "main";
    public static final String SHIELDS = "shields";

    @Entry(category = MAIN)
    public static tooltip_mode cooldown_tooltip_mode = tooltip_mode.NORMAL;

    @Entry(category = MAIN)
    public static tooltip_mode movement_tooltip_mode = tooltip_mode.DISABLED;

    public enum tooltip_mode {
        DISABLED, NORMAL, COMPACT
    }

    @Comment(category = MAIN)
    @SuppressWarnings("unused") public static Comment universal_disabling_description;

    @Entry(category = MAIN)
    public static boolean universal_disabling = true;

    @Entry(category = SHIELDS, min = 1) public static int vanilla_shield_durability = 336;
    @SuppressWarnings("unused") @Comment(category = SHIELDS, centered = true) public static Comment enchantability_convention;
    @Entry(category = SHIELDS, min = 0) public static int vanilla_shield_enchantability = 14;
    @Entry(category = SHIELDS, min = 0F, max = 2.0F, isSlider = true) public static float  vanilla_shield_movement_multiplier = 0.2F;
    @Entry(category = SHIELDS, min = 0F, max = 360F, isSlider = true) public static float  vanilla_shield_blocking_angle = 90.0F;
    @Entry(category = SHIELDS, min = 0) public static int vanilla_shield_cooldown_ticks = 100;
    @Entry(category = SHIELDS, min = 0F, max = 2.0F, isSlider = true) public static float  tower_movement_multiplier = 0.2F;
    @Entry(category = SHIELDS, min = 0F, max = 360F, isSlider = true) public static float  tower_blocking_angle = 90.0F;
    @Entry(category = SHIELDS, min = 0F, max = 2.0F, isSlider = true) public static float  buckler_movement_multiplier = 0.75F;
    @Entry(category = SHIELDS, min = 0F, max = 360F, isSlider = true) public static float  buckler_blocking_angle = 90;
    @Entry(category = SHIELDS, min = 0F, max = 2.0F, isSlider = true) public static float  heater_movement_multiplier = 0.5F;
    @Entry(category = SHIELDS, min = 0F, max = 360F, isSlider = true) public static float  heater_blocking_angle = 29.7F;
}
