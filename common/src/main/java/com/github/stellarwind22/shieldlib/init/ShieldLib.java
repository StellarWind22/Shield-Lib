package com.github.stellarwind22.shieldlib.init;

import com.github.stellarwind22.shieldlib.lib.component.ShieldInformation;
import com.github.stellarwind22.shieldlib.lib.component.ShieldLibDataComponents;
import com.github.stellarwind22.shieldlib.lib.config.ShieldLibConfig;
import com.github.stellarwind22.shieldlib.lib.event.ShieldEvents;
import com.github.stellarwind22.shieldlib.lib.object.BlocksAttacksCooldownModifier;
import com.github.stellarwind22.shieldlib.lib.object.BlocksAttacksMovementModifier;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibDamage;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibTags;
import com.github.stellarwind22.shieldlib.test.ShieldLibTests;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
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

        ShieldLib.registerMovementModifier((player, stack, blocksAttacks, movement) -> {

            if (stack.is(Items.SHIELD)) {
                return movement.scale(ShieldLibConfig.vanilla_shield_movement_multiplier * 5.0f);
            }

            if (!stack.has(ShieldLibDataComponents.SHIELD_INFORMATION.get())) return movement;

            ShieldInformation shieldInfo = stack.get(ShieldLibDataComponents.SHIELD_INFORMATION.get());
            if (shieldInfo == null || !shieldInfo.hasFeature("config")) return movement;

            float multiplier = switch (shieldInfo.type()) {
                case "tower" -> ShieldLibConfig.tower_movement_multiplier;
                case "buckler" -> ShieldLibConfig.buckler_movement_multiplier;
                case "heater" -> ShieldLibConfig.heater_movement_multiplier;
                case "targe" -> ShieldLibConfig.targe_movement_multiplier;
                default -> 1.0F;
            };

            return movement.scale(multiplier * 5.0F);
        });

        ShieldLib.registerCooldownModifier(((player, stack, blocksAttacks, currentCooldown) -> {

            if(stack.is(Items.SHIELD)) {
                return currentCooldown * (ShieldLibConfig.vanilla_shield_cooldown_seconds / 5.0F);
            }
            return currentCooldown;
        }));

        if(IS_DEV) {
            ShieldLibTests.init();
        }

        ShieldEvents.BLOCK.register((level, defender, source, amount, hand, shield) -> {
            if (!shield.has(ShieldLibDataComponents.SHIELD_INFORMATION.get())) return;

            ShieldInformation shieldInfo = shield.get(ShieldLibDataComponents.SHIELD_INFORMATION.get());
            if (shieldInfo == null) return;

            if (!(shieldInfo.hasFeature("spiked") && shieldInfo.hasFeature("config"))) return;

            Entity attacker = source.getEntity();
            if (attacker == null) return;

            float damage = switch (shieldInfo.type()) {
                case "tower" -> ShieldLibConfig.tower_spiked_hit_damage;
                case "buckler" -> ShieldLibConfig.buckler_spiked_hit_damage;
                case "heater" -> ShieldLibConfig.heater_spiked_hit_damage;
                case "targe" -> ShieldLibConfig.targe_spiked_hit_damage;
                default -> 0F;
            };

            if (damage > 0F) {
                attacker.hurtServer(
                        level,
                        ShieldLibDamage.sourceOf(level.registryAccess(), ShieldLibDamage.HIT_SPIKED_SHIELD, defender, attacker),
                        damage
                );
            }
        });

        ShieldEvents.COLLIDE.register((level, player, collider, withinAngle, hand, shield) -> {
            if (!shield.has(ShieldLibDataComponents.SHIELD_INFORMATION.get())) return;
            if (!withinAngle) return;

            ShieldInformation shieldInfo = shield.get(ShieldLibDataComponents.SHIELD_INFORMATION.get());
            if (shieldInfo == null) return;

            boolean spiked = shieldInfo.hasFeature("spiked") && shieldInfo.hasFeature("config");
            if (!spiked) return;

            // Determine damage based on shield type
            float damage = switch (shieldInfo.type()) {
                case "tower" -> ShieldLibConfig.tower_spiked_collide_damage;
                case "buckler" -> ShieldLibConfig.buckler_spiked_collide_damage;
                case "heater" -> ShieldLibConfig.heater_spiked_collide_damage;
                case "targe" -> ShieldLibConfig.targe_spiked_collide_damage;
                default -> 0F;
            };

            if (damage > 0F) {
                // Apply damage
                collider.hurtServer(
                        level,
                        ShieldLibDamage.sourceOf(level.registryAccess(), ShieldLibDamage.COLLIDE_SPIKED_SHIELD, player, collider),
                        damage
                );

                // Play shield block sound using BlocksAttacks
                BlocksAttacks blocksAttacks = shield.get(DataComponents.BLOCKS_ATTACKS);
                assert blocksAttacks != null;
                SoundEvent blockSound = blocksAttacks.blockSound().orElseGet(() -> Holder.direct(SoundEvents.ANVIL_LAND)).value();
                level.playSound(null, player.getX(), player.getY(), player.getZ(), blockSound, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        });

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

    public static float getCooldownSecondsWithModifiers(Player player, ItemStack stack, BlocksAttacks blocksAttacks) {
        float cooldown = blocksAttacks.disableCooldownScale() * 5.0F;
        for(BlocksAttacksCooldownModifier cooldownModifier : cooldownModifiers) {
            cooldown = cooldownModifier.modify(player, stack, blocksAttacks, cooldown);
        }
        return cooldown;
    }
}
