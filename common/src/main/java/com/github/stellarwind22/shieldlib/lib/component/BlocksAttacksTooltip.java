package com.github.stellarwind22.shieldlib.lib.component;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.function.Consumer;

public class BlocksAttacksTooltip implements ShieldTooltipAppender {

    @Override
    public DataComponentType<?> getComponentType() {
        return DataComponents.BLOCKS_ATTACKS;
    }

    @Override
    public void append(ItemStack stack, DataComponentType<?> componentType, Consumer<Component> adder, TooltipFlag flag) {

    }

    @Override
    public void remove(List<Component> tooltip) {

    }
}
