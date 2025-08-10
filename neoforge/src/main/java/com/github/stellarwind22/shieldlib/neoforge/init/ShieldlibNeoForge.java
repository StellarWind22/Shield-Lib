package com.github.stellarwind22.shieldlib.neoforge.init;

import com.github.stellarwind22.shieldlib.init.Shieldlib;
import net.neoforged.fml.common.Mod;

@Mod(Shieldlib.MOD_ID)
public final class ShieldlibNeoForge {

    public ShieldlibNeoForge() {
        // Run our common setup.
        Shieldlib.init(false);
    }
}
