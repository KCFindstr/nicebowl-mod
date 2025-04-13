package com.rabimimi.nicebowl.client;

import com.rabimimi.nicebowl.items.ItemRegistry;

public class NiceBowlClient {
  public static void onInitializeClient() {
    ItemRegistry.postInit();
  }
}
