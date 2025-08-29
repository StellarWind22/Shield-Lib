package com.github.stellarwind22.shieldlib.lib.registry;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.phys.Vec2;

@FunctionalInterface
public interface ShieldMovementModifier {
    Vec2 modify(Player player, ItemStack stack, BlocksAttacks blocksAttacks, Vec2 movement);
}
