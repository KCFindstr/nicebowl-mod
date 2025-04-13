package com.rabimimi.nicebowl;

import com.rabimimi.nicebowl.blocks.BlockRegistry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rabimimi.nicebowl.blocks.BlockEntityRegistry;
import com.rabimimi.nicebowl.fluids.FluidRegistry;
import com.rabimimi.nicebowl.items.ItemRegistry;
import com.rabimimi.nicebowl.potions.EffectRegistry;

import net.minecraft.util.Identifier;

public class NiceBowlMod {

  public static final String MOD_ID = "nicebowl";
  public static final Logger LOGGER = LogManager.getLogger(NiceBowlMod.MOD_ID);

  public static Identifier id(String path) {
    return new Identifier(MOD_ID, path);
  }

  public static void init() {
    // Register fluids
    FluidRegistry.FLUIDS.register();

    // Register blocks
    BlockRegistry.BLOCKS.register();

    // Register block entities
    BlockEntityRegistry.BLOCK_ENTITIES.register();

    // Register items
    ItemRegistry.TABS.register();
    ItemRegistry.ITEMS.register();

    // Register effects
    EffectRegistry.EFFECTS.register();
  }
}
