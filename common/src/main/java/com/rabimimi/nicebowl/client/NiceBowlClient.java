package com.rabimimi.nicebowl.client;

import com.rabimimi.nicebowl.items.ItemRegistry;
import com.rabimimi.nicebowl.blocks.BlockRegistry;
import com.rabimimi.nicebowl.fluids.FluidRegistry;

import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.minecraft.client.render.RenderLayer;

public class NiceBowlClient {
  public static void onInitializeClient() {
    ItemRegistry.postInit();
    RenderTypeRegistry.register(RenderLayer.getTranslucent(),
        BlockRegistry.JUICE.get(),
        BlockRegistry.NICE_BOWL.get());
    RenderTypeRegistry.register(RenderLayer.getTranslucent(),
        FluidRegistry.JUICE.get(),
        FluidRegistry.FLOWING_JUICE.get());
  }
}
