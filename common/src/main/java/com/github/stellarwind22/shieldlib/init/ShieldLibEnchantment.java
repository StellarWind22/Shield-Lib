package com.github.stellarwind22.shieldlib.init;

import com.github.stellarwind22.shieldlib.lib.object.ShieldEnchReg;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ShieldLibEnchantment {

    private static final List<ShieldEnchReg> registrations = new ArrayList<>();

    public static void registerAll(HolderSet<Item> holders) {

        for(ShieldEnchReg registration : registrations) {
            registration.accept(holders);
        }
    }

    public static void register(ShieldEnchReg registration) {
        registrations.add(registration);
    }
}
