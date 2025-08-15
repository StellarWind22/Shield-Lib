package com.github.stellarwind22.shieldlib.lib.event;

import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ShieldPreDisableEvent {

    Event<PreDisable> EVENT = EventFactory.createEventResult();

    interface PreDisable {
        EventResult onPreDisable(ServerLevel level, LivingEntity attacker, Player defender, InteractionHand hand, ItemStack shield);
    }
}
