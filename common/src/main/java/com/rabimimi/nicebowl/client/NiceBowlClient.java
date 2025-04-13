package com.rabimimi.nicebowl.client;

import com.rabimimi.nicebowl.items.ItemRegistry;
import com.rabimimi.nicebowl.blocks.BlockRegistry;
import com.rabimimi.nicebowl.fluids.FluidRegistry;

import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class NiceBowlClient {
  public static void onInitializeClient() {
    ItemRegistry.postInit();

    // Render layers
    RenderTypeRegistry.register(RenderLayer.getTranslucent(),
        BlockRegistry.JUICE.get(),
        BlockRegistry.NICE_BOWL.get());
    RenderTypeRegistry.register(RenderLayer.getTranslucent(),
        FluidRegistry.JUICE.get(),
        FluidRegistry.FLOWING_JUICE.get());
  }
}
