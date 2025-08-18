package com.github.stellarwind22.shieldlib.lib.object;

import net.minecraft.core.HolderSet;
import net.minecraft.world.item.Item;

import java.util.function.Consumer;

@FunctionalInterface
public interface ShieldEnchReg extends Consumer<HolderSet<Item>> {}
