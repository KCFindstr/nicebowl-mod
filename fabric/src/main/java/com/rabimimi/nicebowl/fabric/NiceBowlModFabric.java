package com.rabimimi.nicebowl.fabric;

import com.rabimimi.nicebowl.NiceBowlMod;

import net.fabricmc.api.ModInitializer;

public final class NiceBowlModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        NiceBowlMod.init();
    }
}
