package com.github.stellarwind22.shieldlib.lib.event;

import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ShieldEvents {

    Event<ShieldBlock> BLOCK = EventFactory.createLoop();
    Event<ShieldCanBlock> CAN_BLOCK = EventFactory.createCompoundEventResult();
    Event<ShieldAttack> ATTACK = EventFactory.createLoop();
    Event<ShieldCollide> COLLIDE = EventFactory.createLoop();
    Event<ShieldDisable> DISABLE = EventFactory.createLoop();

    interface ShieldBlock {
        void onBlock(ServerLevel level, LivingEntity defender, DamageSource source, float amount, InteractionHand hand, ItemStack shield);
    }

    interface ShieldCanBlock {
        CompoundEventResult<Boolean> tryBlock(ServerLevel level, LivingEntity defender, DamageSource source, float amount, InteractionHand hand, ItemStack shield);
    }

    interface ShieldAttack {
        void onAttack(ServerLevel level, DamageSource source, LivingEntity target, float amount, InteractionHand hand, ItemStack shield);
    }

    interface ShieldCollide {
        void onCollide(ServerLevel level, Player defender, Entity collider, boolean withinAngle, InteractionHand hand, ItemStack shield);
    }

    interface ShieldDisable {
        void onDisable(ServerLevel level, LivingEntity attacker, LivingEntity defender, boolean defenderIsPlayer, InteractionHand hand, ItemStack shield, float disableForSeconds);
    }
}
