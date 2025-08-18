package com.github.stellarwind22.shieldlib.lib.component;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.function.Consumer;

public interface ShieldTooltipAppender {

    DataComponentType<?> getComponentType();

    void append(ItemStack stack, DataComponentType<?> componentType, Consumer<Component> adder, TooltipFlag flag);

    void remove(List<Component> tooltip);
}
