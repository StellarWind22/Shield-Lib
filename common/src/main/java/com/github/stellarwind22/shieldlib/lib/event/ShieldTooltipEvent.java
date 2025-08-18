package com.github.stellarwind22.shieldlib.lib.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public interface ShieldTooltipEvent {

    Event<ShieldTooltip> EVENT = EventFactory.createEventResult();

    interface ShieldTooltip {
        EventResult OnGetTooltip(ItemStack stack, Item.TooltipContext tooltipContext, TooltipFlag tooltipFlag, List<Component> components);
    }
}
