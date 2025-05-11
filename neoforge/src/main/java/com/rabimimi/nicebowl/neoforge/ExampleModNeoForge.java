package com.rabimimi.nicebowl.neoforge;

import net.neoforged.fml.common.Mod;

import com.rabimimi.nicebowl.ExampleMod;

@Mod(ExampleMod.MOD_ID)
public final class ExampleModNeoForge {
    public ExampleModNeoForge() {
        // Run our common setup.
        ExampleMod.init();
    }
}
