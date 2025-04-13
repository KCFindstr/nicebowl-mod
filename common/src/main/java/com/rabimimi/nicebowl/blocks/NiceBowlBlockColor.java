package com.rabimimi.nicebowl.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

import com.rabimimi.nicebowl.utils.Constants;

public class NiceBowlBlockColor implements BlockColorProvider {

  public static final NiceBowlBlockColor INSTANCE = new NiceBowlBlockColor();

  private NiceBowlBlockColor() {
  }

  @Override
  public int getColor(BlockState state, BlockRenderView view, BlockPos pos, int tintIndex) {
    if (tintIndex == 0 && state.get(NiceBowlBlock.LEVEL) == 1) {
      return Constants.JUICE_COLOR_TINT;
    }
    return 0xFFFFFFFF;
  }
}
