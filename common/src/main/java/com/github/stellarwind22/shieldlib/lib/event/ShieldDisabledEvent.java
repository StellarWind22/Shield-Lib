package com.github.stellarwind22.shieldlib.lib.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ShieldDisabledEvent {

    Event<Disable> EVENT = EventFactory.createEventResult();

    interface Disable {

        EventResult onDisable(Player defender, InteractionHand hand, ItemStack shield, int cooldownTicks);
    }
}
