package com.kcfindstr.nicebowl.blocks;

import com.kcfindstr.nicebowl.utils.Constants;

import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistry {
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);
  public static final RegistryObject<NiceBowlBlock> niceBowl = BLOCKS.register("nicebowl_block", NiceBowlBlock::new);
  public static final RegistryObject<FlowingFluidBlock> juice = BLOCKS.register("juice_block", JuiceBlock::new);
}