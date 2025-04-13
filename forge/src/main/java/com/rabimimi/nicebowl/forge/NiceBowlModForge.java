package com.rabimimi.nicebowl.forge;

import com.rabimimi.nicebowl.NiceBowlMod;
import com.rabimimi.nicebowl.blocks.BlockRegistry;
import com.rabimimi.nicebowl.blocks.NiceBowlBlockColor;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(NiceBowlMod.MOD_ID)
public final class NiceBowlModForge {
  public NiceBowlModForge(FMLJavaModLoadingContext context) {
    IEventBus modEventBus = context.getModEventBus();
    EventBuses.registerModEventBus(NiceBowlMod.MOD_ID, modEventBus);
    NiceBowlMod.init();
  }

  @SubscribeEvent
  public void registerBlockColors(RegisterColorHandlersEvent.Block event) {
    event.register(NiceBowlBlockColor.INSTANCE, BlockRegistry.NICE_BOWL.get());
  }
}
