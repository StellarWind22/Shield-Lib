package com.github.stellarwind22.shieldlib.lib.event;

import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ShieldAttackEvent {

    Event<ShieldAttack> EVENT = EventFactory.createCompoundEventResult();

    interface ShieldAttack {
        CompoundEventResult<Float> onAttack(ServerLevel level, LivingEntity attacker, LivingEntity target, float amount, InteractionHand hand, ItemStack shield);
    }
}
