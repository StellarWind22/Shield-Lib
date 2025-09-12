package com.github.stellarwind22.shieldlib.init;

import com.github.stellarwind22.shieldlib.lib.component.ShieldDataComponents;
import com.github.stellarwind22.shieldlib.lib.component.ShieldInformation;
import com.github.stellarwind22.shieldlib.lib.config.ShieldLibConfig;
import com.github.stellarwind22.shieldlib.lib.event.ShieldEvents;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibUtils;
import com.github.stellarwind22.shieldlib.lib.registry.ShieldCooldownEntry;
import com.github.stellarwind22.shieldlib.lib.registry.ShieldCooldownModifier;
import com.github.stellarwind22.shieldlib.lib.registry.ShieldMovementModifier;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibDamage;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibTags;
import com.github.stellarwind22.shieldlib.test.ShieldLibTests;
import com.mojang.datafixers.util.Pair;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.phys.Vec2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ShieldLib {

    private static final List<ShieldCooldownModifier> cooldownModifiers = new ArrayList<>();
    private static final List<ShieldMovementModifier> movementModifiers = new ArrayList<>();
    private static final List<ShieldCooldownEntry> cooldownEntries = new ArrayList<>();

    public static final String MOD_ID = "shieldlib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static boolean IS_DEV;

    public static void init(boolean isDev) {

        IS_DEV = isDev;

        // Write common init code here.
        ShieldLibTags.init();
        ShieldDataComponents.init();

        ShieldLib.registerCooldownEntry(
                new ShieldCooldownEntry(ShieldLibUtils.VANILLA_SHIELD_INFORMATION_COMPONENT,               ShieldLibTags.C_AXE,  () -> ShieldLibConfig.vanilla_shield_cooldown_seconds),
                new ShieldCooldownEntry(ShieldLibUtils.CONFIG_TOWER_SHIELD_INFORMATION_COMPONENT,          ShieldLibTags.C_AXE,  () -> ShieldLibConfig.tower_default_cooldown_seconds),
                new ShieldCooldownEntry(ShieldLibUtils.CONFIG_BUCKLER_SHIELD_INFORMATION_COMPONENT,        ShieldLibTags.C_AXE,  () -> ShieldLibConfig.buckler_default_cooldown_seconds),
                new ShieldCooldownEntry(ShieldLibUtils.CONFIG_HEATER_SHIELD_INFORMATION_COMPONENT,         ShieldLibTags.C_AXE,  () -> ShieldLibConfig.heater_default_cooldown_seconds),
                new ShieldCooldownEntry(ShieldLibUtils.CONFIG_TARGE_SHIELD_INFORMATION_COMPONENT,          ShieldLibTags.C_AXE,  () -> ShieldLibConfig.targe_default_cooldown_seconds)
        );

        ShieldLib.registerMovementModifier((player, stack, blocksAttacks, movement) -> {

            if (stack.is(Items.SHIELD)) {
                return movement.scale(ShieldLibConfig.vanilla_shield_movement_multiplier * 5.0f);
            }

            if (!stack.has(ShieldDataComponents.SHIELD_INFORMATION.get())) return movement;

            ShieldInformation shieldInfo = stack.get(ShieldDataComponents.SHIELD_INFORMATION.get());
            if (shieldInfo == null || !shieldInfo.hasFeature("config")) return movement;

            float multiplier = switch (shieldInfo.type()) {
                case "tower" -> ShieldLibConfig.tower_movement_multiplier;
                case "buckler" -> ShieldLibConfig.buckler_movement_multiplier;
                case "heater" -> ShieldLibConfig.heater_movement_multiplier;
                case "targe" -> ShieldLibConfig.targe_movement_multiplier;
                default -> 0F;
            };

            //Don't modify anything if there isn't a multiplier
            if(multiplier <= 0F) return  movement;

            return movement.scale(multiplier * 5F);
        });

        ShieldEvents.CAN_BLOCK.register(((level, defender, source, amount, hand, shield) -> {
            if(!ShieldLibConfig.buckler_blocks_arrows) {
                if (!shield.has(ShieldDataComponents.SHIELD_INFORMATION.get())) return EventResult.pass();
                ShieldInformation shieldInfo = shield.get(ShieldDataComponents.SHIELD_INFORMATION.get());
                assert shieldInfo != null;

                Entity entity = source.getDirectEntity();

                if(entity instanceof AbstractArrow && shieldInfo.isType("buckler")) {
                    return EventResult.interruptFalse();
                }
            }

            return EventResult.pass();
        }));

        ShieldEvents.BLOCK.register((level, defender, source, amount, hand, shield) -> {
            if (!shield.has(ShieldDataComponents.SHIELD_INFORMATION.get())) return;

            ShieldInformation shieldInfo = shield.get(ShieldDataComponents.SHIELD_INFORMATION.get());
            assert shieldInfo != null;

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

        ShieldEvents.BLOCK_FAIL.register(((level, defender, source, amount, hand, shield) -> {
            if(!ShieldLibConfig.buckler_blocks_arrows) {

                //Safely grab components
                if (!shield.has(ShieldDataComponents.SHIELD_INFORMATION.get())) return;
                if (!shield.has(DataComponents.BLOCKS_ATTACKS)) return;

                ShieldInformation shieldInfo = shield.get(ShieldDataComponents.SHIELD_INFORMATION.get());
                BlocksAttacks blocksAttacks = shield.get(DataComponents.BLOCKS_ATTACKS);

                assert shieldInfo != null;
                assert blocksAttacks != null;

                Entity entity = source.getDirectEntity();

                //If arrow & buckler
                if(entity instanceof AbstractArrow && shieldInfo.isType("buckler")) {

                    blocksAttacks.hurtBlockingItem(level, shield, defender, hand, amount);

                    if(source.getEntity() instanceof LivingEntity attacker) {
                        ShieldEvents.DISABLE.invoker().onDisable(level, attacker, defender, defender instanceof Player, hand, shield, 1F);
                    }
                    blocksAttacks.disable(level, defender, 1F, shield);
                }
            }
        }));

        ShieldEvents.COLLIDE.register((level, player, collider, withinAngle, hand, shield) -> {
            if (!shield.has(ShieldDataComponents.SHIELD_INFORMATION.get())) return;
            if (!withinAngle) return;

            ShieldInformation shieldInfo = shield.get(ShieldDataComponents.SHIELD_INFORMATION.get());
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
                if(collider.hurtServer(
                        level,
                        ShieldLibDamage.sourceOf(level.registryAccess(), ShieldLibDamage.COLLIDE_SPIKED_SHIELD, collider, player),
                        damage
                ))
                {
                    // Play shield block sound using BlocksAttacks
                    BlocksAttacks blocksAttacks = shield.get(DataComponents.BLOCKS_ATTACKS);
                    assert blocksAttacks != null;
                    SoundEvent blockSound = blocksAttacks.blockSound().orElseGet(() -> Holder.direct(SoundEvents.ANVIL_LAND)).value();
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), blockSound, SoundSource.PLAYERS, 1.0F, 1.0F);
                }
            }
        });

        EntityEvent.LIVING_HURT.register((target, source, amount) -> {
            if(source.getEntity() instanceof LivingEntity attacker && target.level() instanceof ServerLevel serverLevel) {
                ShieldEvents.ATTACK.invoker().onAttack(serverLevel, source, target, amount, attacker.getWeaponItem());
            }
            return EventResult.pass();
        });

        if(IS_DEV) {
            ShieldLibTests.init();
        }
    }

    @SuppressWarnings("unused")
    public static void registerCooldownEntry(ShieldCooldownEntry entry) {
        cooldownEntries.add(entry);
    }

    public static void registerCooldownEntry(ShieldCooldownEntry... entries) {
        cooldownEntries.addAll(List.of(entries));
    }

    public static void registerCooldownModifier(ShieldCooldownModifier cooldownModifier) {
        cooldownModifiers.add(cooldownModifier);
    }

    public static void registerMovementModifier(ShieldMovementModifier movementModifier) {
        movementModifiers.add(movementModifier);
    }

    public static float getCooldownSeconds(RegistryAccess access, ItemStack weapon) {
        for(ShieldCooldownEntry cooldownEntry : cooldownEntries) {
            if(cooldownEntry.matchWeapon(access, weapon)) return cooldownEntry.cooldown().get();
        }
        return 0;
    }

    @SuppressWarnings("unused")
    public static float getCooldownSeconds(ItemStack shield, BlocksAttacks blocksAttacks) {
        for(ShieldCooldownEntry cooldownEntry : cooldownEntries) {
            if(cooldownEntry.matchShield(shield)) return cooldownEntry.cooldown().get();
        }
        return blocksAttacks.disableCooldownScale() * 5.0F;
    }

    public static Vec2 getMovementWithModifiers(Player player, ItemStack stack, BlocksAttacks blocksAttacks, Vec2 movement) {
        for(ShieldMovementModifier movementModifier : movementModifiers) {
            movement = movementModifier.modify(player, stack, blocksAttacks, movement);
        }
        return movement;
    }

    public static float getCooldownSecondsWithModifiers(Player player, ItemStack stack, BlocksAttacks blocksAttacks, float cooldown) {
        for(ShieldCooldownModifier cooldownModifier : cooldownModifiers) {
            cooldown = cooldownModifier.modify(player, stack, blocksAttacks, cooldown);
        }
        return cooldown;
    }

    @Environment(EnvType.CLIENT)
    public static Set<Pair<String, Float>> getTagTranslations(ShieldInformation shieldInformation) {
        Set<Pair<String, Float>> set = new HashSet<>();

        cooldownEntries.forEach((cooldownEntry) -> {
            if(cooldownEntry.matchShield(shieldInformation)) set.add(new Pair<>(ShieldLibUtils.getTranslationKey(cooldownEntry.tag()), cooldownEntry.cooldown().get()));
        });

        return set;
    }
}
