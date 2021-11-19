package com.kcfindstr.nicebowl.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.kcfindstr.nicebowl.fluids.FluidRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;

public class JuiceBlock extends FlowingFluidBlock {
  public JuiceBlock() {
    super(FluidRegistry.juice, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops());
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new NiceBowlTileEntity();
  }
  
  @Override
  public void appendHoverText(ItemStack itemStack, @Nullable IBlockReader reader, List<ITextComponent> text, ITooltipFlag tooltip) {
    super.appendHoverText(itemStack, reader, text, tooltip);
    NiceBowlBlock.addHoverText(itemStack, text);
  }
}
