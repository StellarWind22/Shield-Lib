package com.github.stellarwind22.shieldlib.init;

import com.github.stellarwind22.shieldlib.lib.component.ShieldInformation;
import com.github.stellarwind22.shieldlib.lib.component.ShieldLibDataComponents;
import com.github.stellarwind22.shieldlib.lib.config.ShieldLibConfig;
import com.github.stellarwind22.shieldlib.lib.object.BlocksAttacksCooldownModifier;
import com.github.stellarwind22.shieldlib.lib.object.BlocksAttacksMovementModifier;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibTags;
import com.github.stellarwind22.shieldlib.test.ShieldLibTests;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.phys.Vec2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public final class ShieldLib {

    private static final List<BlocksAttacksCooldownModifier> cooldownModifiers = new ArrayList<>();
    private static final List<BlocksAttacksMovementModifier> movementModifiers = new ArrayList<>();

    public static final String MOD_ID = "shieldlib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static boolean IS_DEV;

    public static void init(boolean isDev) {

        IS_DEV = isDev;

        // Write common init code here.
        ShieldLibTags.init();
        ShieldLibDataComponents.init();

        ShieldLib.registerMovementModifier(((player, stack, blocksAttacks, movement) -> {

            if(stack.is(Items.SHIELD)) {
                return movement.scale(ShieldLibConfig.vanilla_shield_movement_multiplier * 5.0F);
            }

            if(stack.has(ShieldLibDataComponents.SHIELD_INFORMATION.get())) {
                ShieldInformation shieldInformation = stack.get(ShieldLibDataComponents.SHIELD_INFORMATION.get());
                assert shieldInformation != null;

                if(shieldInformation.isType("tower") && shieldInformation.hasFeature("config")) {
                    return movement.scale(ShieldLibConfig.tower_movement_multiplier * 5.0F);
                }
                if(shieldInformation.isType("buckler") && shieldInformation.hasFeature("config")) {
                    return movement.scale(ShieldLibConfig.buckler_movement_multiplier * 5.0F);
                }
                if(shieldInformation.isType("heater") && shieldInformation.hasFeature("config")) {
                    return movement.scale(ShieldLibConfig.heater_movement_multiplier * 5.0F);

                } else if (shieldInformation.isType("targe") && shieldInformation.hasFeature("config")) {
                    return movement.scale(ShieldLibConfig.targe_movement_multiplier * 5.0F);
                }
            }
            return movement;
        }));

        ShieldLib.registerCooldownModifier(((player, stack, blocksAttacks, currentCooldown) -> {

            if(stack.is(Items.SHIELD)) {
                return currentCooldown * ((float) ShieldLibConfig.vanilla_shield_cooldown_ticks / 100.0F);
            }
            return currentCooldown;
        }));

        if(IS_DEV) {
            ShieldLibTests.init();
        }
    }

    public static void registerCooldownModifier(BlocksAttacksCooldownModifier cooldownModifier) {
        cooldownModifiers.add(cooldownModifier);
    }

    public static void registerMovementModifier(BlocksAttacksMovementModifier movementModifier) {
        movementModifiers.add(movementModifier);
    }

    public static Vec2 getMovementWithModifiers(Player player, ItemStack stack, BlocksAttacks blocksAttacks, Vec2 movement) {
        for(BlocksAttacksMovementModifier movementModifier : movementModifiers) {
            movement = movementModifier.modify(player, stack, blocksAttacks, movement);
        }
        return movement;
    }

    public static float getCooldownSecondsWithModifiers(Player player, ItemStack stack, BlocksAttacks blocksAttacks, float cooldown) {
        for(BlocksAttacksCooldownModifier cooldownModifier : cooldownModifiers) {
            cooldown = cooldownModifier.modify(player, stack, blocksAttacks, cooldown);
        }
        return cooldown;
    }

    public static float getCooldownTicksWithModifiers(Player player, ItemStack stack, BlocksAttacks blocksAttacks) {
        float cooldown = 100.F * blocksAttacks.disableCooldownScale();
        for(BlocksAttacksCooldownModifier cooldownModifier : cooldownModifiers) {
            cooldown = cooldownModifier.modify(player, stack, blocksAttacks, cooldown);
        }
        return cooldown;
    }
}
