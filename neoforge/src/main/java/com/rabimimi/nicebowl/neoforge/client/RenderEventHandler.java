package com.rabimimi.nicebowl.neoforge.client;

import com.rabimimi.nicebowl.NiceBowlMod;
import com.rabimimi.nicebowl.events.PlayerEventHandler;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.FogShape;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = NiceBowlMod.MOD_ID, value = Dist.CLIENT)
public class RenderEventHandler {

  @SubscribeEvent(receiveCanceled = true)
  public static void onFogColor(ViewportEvent.ComputeFogColor event) {
    PlayerEventHandler.getFogColor(MinecraftClient.getInstance().player).ifPresent(c -> {
      event.setRed(((c >> 16) & 0xff) / 255f);
      event.setGreen(((c >> 8) & 0xff) / 255f);
      event.setBlue((c & 0xff) / 255f);
    });
  }

  @SubscribeEvent(receiveCanceled = true)
  public static void onFogColor(ViewportEvent.RenderFog event) {
    PlayerEventHandler.getFogDensity(MinecraftClient.getInstance().player).ifPresent(c -> {
      event.setFogShape(FogShape.SPHERE);
      event.setNearPlaneDistance(MathHelper.lerp(c, 1F, 20.0F)); // Closer = denser fog
      event.setFarPlaneDistance(20.0F); // Further = thinner fog
    });
  }
}
