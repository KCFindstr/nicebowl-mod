package com.kcfindstr.nicebowl;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.kcfindstr.nicebowl.blocks.BlockRegistry;
import com.kcfindstr.nicebowl.blocks.TileEntityRegistry;
import com.kcfindstr.nicebowl.fluids.FluidRegistry;
import com.kcfindstr.nicebowl.items.ItemRegistry;
import com.kcfindstr.nicebowl.utils.Constants;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Constants.MOD_ID)
public class NiceBowlMod {
  public NiceBowlMod() {
    // Register the setup method for modloading
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    // Register the doClientStuff method for modloading
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

    // Register ourselves for server and other game events we are interested in
    MinecraftForge.EVENT_BUS.register(this);

    BlockRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    ItemRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    FluidRegistry.FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
    TileEntityRegistry.TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
  }

  private void setup(final FMLCommonSetupEvent event) {
    // some preinit code
  }

  private void clientSetup(final FMLClientSetupEvent event) {
    RenderTypeLookup.setRenderLayer(FluidRegistry.juice.get(), RenderType.translucent());
    RenderTypeLookup.setRenderLayer(FluidRegistry.juiceFlowing.get(), RenderType.translucent());
    RenderTypeLookup.setRenderLayer(BlockRegistry.niceBowl.get(), RenderType.translucent());
  }

  // You can use SubscribeEvent and let the Event Bus discover methods to call
  @SubscribeEvent
  public void onServerStarting(FMLServerStartingEvent event) {
    // do something when the server starts
  }
}
