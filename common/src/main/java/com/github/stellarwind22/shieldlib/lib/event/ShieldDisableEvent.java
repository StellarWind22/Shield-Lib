package com.github.stellarwind22.shieldlib.lib.event;

import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ShieldDisableEvent {

    Event<PreDisable> EVENT = EventFactory.createCompoundEventResult();

    interface PreDisable {
        CompoundEventResult<Float> onDisable(ServerLevel level, LivingEntity attacker, LivingEntity defender, boolean defenderIsPlayer, InteractionHand hand, ItemStack shield, float disableForSeconds);
    }
}
