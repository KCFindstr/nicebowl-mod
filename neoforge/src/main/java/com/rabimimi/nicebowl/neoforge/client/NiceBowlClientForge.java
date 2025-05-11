package com.rabimimi.nicebowl.neoforge.client;

import com.rabimimi.nicebowl.NiceBowlMod;
import com.rabimimi.nicebowl.blocks.BlockRegistry;
import com.rabimimi.nicebowl.blocks.NiceBowlBlockColor;
import com.rabimimi.nicebowl.client.NiceBowlClient;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = NiceBowlMod.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class NiceBowlClientForge {

  @SubscribeEvent
  public static void onClientSetup(FMLClientSetupEvent event) {
    NiceBowlClient.onInitializeClient();
  }

  @SubscribeEvent
  public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
    event.register(NiceBowlBlockColor.INSTANCE, BlockRegistry.NICE_BOWL.get());
  }
}
