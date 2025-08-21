package com.github.stellarwind22.shieldlib.lib.object;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlocksAttacks;

@FunctionalInterface
public interface ShieldCooldownModifier {
    float modify(Player player, ItemStack shield, BlocksAttacks blocksAttacks, float currentCooldown);
}
