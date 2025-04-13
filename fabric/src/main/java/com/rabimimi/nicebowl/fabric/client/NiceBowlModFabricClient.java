package com.rabimimi.nicebowl.fabric.client;

import com.rabimimi.nicebowl.blocks.BlockRegistry;
import com.rabimimi.nicebowl.blocks.NiceBowlBlockColor;
import com.rabimimi.nicebowl.client.NiceBowlClient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public final class NiceBowlModFabricClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    ColorProviderRegistry.BLOCK.register(NiceBowlBlockColor.INSTANCE, BlockRegistry.NICE_BOWL.get());

    NiceBowlClient.onInitializeClient();
  }
}
