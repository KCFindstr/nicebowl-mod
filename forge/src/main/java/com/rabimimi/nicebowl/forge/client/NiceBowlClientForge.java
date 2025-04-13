package com.rabimimi.nicebowl.forge.client;

import com.rabimimi.nicebowl.NiceBowlMod;
import com.rabimimi.nicebowl.client.NiceBowlClient;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = NiceBowlMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NiceBowlClientForge {

  @SubscribeEvent
  public static void onClientSetup(FMLClientSetupEvent event) {
    NiceBowlClient.onInitializeClient();
  }
}
