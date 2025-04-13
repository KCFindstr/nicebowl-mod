package com.rabimimi.nicebowl.forge;

import com.rabimimi.nicebowl.NiceBowlMod;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(NiceBowlMod.MOD_ID)
public final class NiceBowlModForge {
  public NiceBowlModForge(FMLJavaModLoadingContext context) {
    IEventBus modEventBus = context.getModEventBus();
    EventBuses.registerModEventBus(NiceBowlMod.MOD_ID, modEventBus);
    NiceBowlMod.init();
  }
}
