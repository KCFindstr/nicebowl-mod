package com.kcfindstr.nicebowl.items;

import com.kcfindstr.nicebowl.utils.Constants;
import com.kcfindstr.nicebowl.utils.PlayerUtils;

import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PropertyRegistry {
  @SubscribeEvent
  public static void propertyOverrideRegistry(FMLClientSetupEvent event) {
    event.enqueueWork(() -> {
      ItemModelsProperties.register(ItemRegistry.niceBowl.get(), 
        new ResourceLocation(Constants.MOD_ID, "used"),
        (itemStack, clientWorld, livingEntity)
          -> PlayerUtils.getPlayer(itemStack) == null ? 0 : 1);
      ItemModelsProperties.register(ItemRegistry.niceBowlHead.get(), 
        new ResourceLocation(Constants.MOD_ID, "used"),
        (itemStack, clientWorld, livingEntity)
          -> PlayerUtils.getPlayer(itemStack) == null ? 0 : 1);
    });
  }
}