package com.rabimimi.nicebowl.neoforge.client;

import com.rabimimi.nicebowl.NiceBowlMod;
import com.rabimimi.nicebowl.blocks.BlockRegistry;
import com.rabimimi.nicebowl.blocks.NiceBowlBlockColor;
import com.rabimimi.nicebowl.client.NiceBowlClient;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = NiceBowlMod.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class NiceBowlClientNeoForge {

  @SubscribeEvent
  public static void onClientSetup(FMLClientSetupEvent event) {
    NiceBowlClient.onInitializeClient();
  }

  @SubscribeEvent
  public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
    event.register(NiceBowlBlockColor.INSTANCE, BlockRegistry.NICE_BOWL.get());
  }
}
