package com.rabimimi.nicebowl.fabric;

import com.rabimimi.nicebowl.NiceBowlMod;

import net.fabricmc.api.ModInitializer;

public class NiceBowlModFabric implements ModInitializer {
  @Override
  public void onInitialize() {
    NiceBowlMod.init();
  }
}
