package com.kcfindstr.nicebowl.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import com.kcfindstr.nicebowl.utils.Constants;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class NiceBowlBlockColor implements IBlockColor
{
  @SubscribeEvent
  public static void registerBlockColors(ColorHandlerEvent.Block event) {
    event.getBlockColors().register(new NiceBowlBlockColor(), BlockRegistry.niceBowl.get());
  }

  @Override
  public int getColor(BlockState state, IBlockDisplayReader reader, BlockPos pos, int tintIndex) {
    if (tintIndex == 0) {
      if (state.getValue(NiceBowlBlock.LEVEL) == 1) {
        return Constants.JUICE_COLOR_TINT;
      }
    }
    return 0xFFFFFFFF;
  }
}
