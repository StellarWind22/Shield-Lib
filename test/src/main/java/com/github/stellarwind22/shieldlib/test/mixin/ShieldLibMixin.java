package com.github.stellarwind22.shieldlib.test.mixin;

import com.github.stellarwind22.shieldlib.init.ShieldLib;
import com.github.stellarwind22.shieldlib.test.ShieldLibTests;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShieldLib.class)
public class ShieldLibMixin {

    @Inject(at = @At("TAIL"), method = "init")
    private static void init(boolean isDev, CallbackInfo ci) {
        if(isDev) {
            ShieldLibTests.init();
        }
    }
}
