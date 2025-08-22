package com.github.stellarwind22.shieldlib.init;

import com.github.stellarwind22.shieldlib.lib.object.BlocksAttacksCooldownModifier;
import com.github.stellarwind22.shieldlib.lib.object.BlocksAttacksMovementModifier;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibTags;
import com.github.stellarwind22.shieldlib.test.ShieldLibTests;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

        if(isDev) {
            ShieldLibTests.init();
            ShieldLib.LOGGER.warn("TEST CODE IS CURRENTLY RUNNING!, IF YOU ARE NOT IN A DEV ENVIRONMENT THIS IS BAD!!");
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
