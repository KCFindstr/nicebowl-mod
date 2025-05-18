package com.rabimimi.nicebowl.blocks;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.rabimimi.nicebowl.fluids.FluidRegistry;

import dev.architectury.core.block.ArchitecturyLiquidBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class JuiceBlock extends ArchitecturyLiquidBlock implements BlockEntityProvider {
  public JuiceBlock() {
    super(FluidRegistry.JUICE, Block.Settings.copy(Blocks.WATER));
  }

  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return NiceBowlBlockEntity.newJuice(pos, state);
  }

  @Override
  public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> text,
      TooltipType options) {
    super.appendTooltip(itemStack, context, text, options);
    NiceBowlBlock.appendTooltip(itemStack, text);
  }

  @Nullable
  public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
    BlockEntity blockEntity = world.getBlockEntity(pos);
    return blockEntity instanceof NamedScreenHandlerFactory ? (NamedScreenHandlerFactory) blockEntity : null;
  }
}
