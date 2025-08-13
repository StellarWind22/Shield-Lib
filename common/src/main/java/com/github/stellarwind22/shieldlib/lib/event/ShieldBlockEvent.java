package com.github.stellarwind22.shieldlib.lib.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ShieldBlockEvent {

    Event<Block> EVENT = EventFactory.createEventResult();

    interface Block {
        EventResult onBlock(LivingEntity defender, DamageSource source, float amount, InteractionHand hand, ItemStack shield);
    }
}
