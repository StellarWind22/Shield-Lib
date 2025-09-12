package com.github.stellarwind22.shieldlib.lib.client.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public interface ShieldClientEvents {

    Event<ShieldTooltip> TOOLTIP = EventFactory.createLoop();

    interface ShieldTooltip {
        void onTooltip(Player player, ItemStack stack, Item.TooltipContext tooltipContext, TooltipFlag tooltipFlag, List<Component> components);
    }
}
