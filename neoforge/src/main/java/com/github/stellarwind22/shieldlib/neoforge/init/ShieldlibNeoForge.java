package com.github.stellarwind22.shieldlib.neoforge.init;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import net.neoforged.fml.common.Mod;

@Mod(ShieldLib.MOD_ID)
public final class ShieldlibNeoForge {

    public ShieldlibNeoForge() {
        // Run our common setup.
        ShieldLib.init(false);
    }
}
