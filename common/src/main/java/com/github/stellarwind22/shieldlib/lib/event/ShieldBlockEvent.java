package com.github.stellarwind22.shieldlib.lib.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ShieldBlockEvent {

    Event<Block> EVENT = EventFactory.createLoop();

    interface Block {
        void onBlock(ServerLevel level, LivingEntity defender, DamageSource source, float amount, InteractionHand hand, ItemStack shield);
    }
}
