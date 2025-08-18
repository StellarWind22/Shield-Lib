package com.github.stellarwind22.shieldlib.fabric.init;

import com.github.stellarwind22.shieldlib.init.ShieldLibEnchantment;
import com.github.stellarwind22.shieldlib.lib.object.ShieldEnchReg;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.Item;

public class ShieldLibFabricEnchantment implements ShieldEnchReg {

    @Override
    public void accept(HolderSet<Item> holders) {
        ShieldLibEnchantment.registerAll(holders);
    }
}
