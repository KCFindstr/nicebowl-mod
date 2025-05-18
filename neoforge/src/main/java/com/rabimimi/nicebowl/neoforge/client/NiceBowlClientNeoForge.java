package com.rabimimi.nicebowl.neoforge.client;

import com.rabimimi.nicebowl.NiceBowlMod;
import com.rabimimi.nicebowl.blocks.BlockRegistry;
import com.rabimimi.nicebowl.blocks.NiceBowlBlockColor;
import com.rabimimi.nicebowl.client.NiceBowlClient;
import com.rabimimi.nicebowl.fluids.FluidRegistry;

import net.minecraft.util.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

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

  // https://github.com/architectury/architectury-api/issues/551
  @SubscribeEvent
  public static void initializeClient(RegisterClientExtensionsEvent event) {
    for (var fluid : FluidRegistry.ALL_FLUIDS) {
      event.registerFluidType(new IClientFluidTypeExtensions() {
        @Override
        public int getTintColor() {
          return fluid.getColor();
        }

        @Override
        public Identifier getStillTexture() {
          return fluid.getSourceTexture();
        }

        @Override
        public Identifier getFlowingTexture() {
          return fluid.getFlowingTexture();
        }
      }, fluid.getFlowingFluid().getFluidType());
    }
  }
}
