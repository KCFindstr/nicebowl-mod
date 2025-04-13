package com.rabimimi.nicebowl.blocks;

import com.rabimimi.nicebowl.NiceBowlMod;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import dev.architectury.core.block.ArchitecturyLiquidBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;

public class BlockRegistry {
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
      NiceBowlMod.MOD_ID, RegistryKeys.BLOCK);

  public static final RegistrySupplier<NiceBowlBlock> NICE_BOWL = BLOCKS.register("nicebowl_block", NiceBowlBlock::new);
  public static final RegistrySupplier<ArchitecturyLiquidBlock> JUICE = BLOCKS.register("juice_block", JuiceBlock::new);
}
