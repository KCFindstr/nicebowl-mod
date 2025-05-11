package com.rabimimi.nicebowl.neoforge.jade;

import com.rabimimi.nicebowl.NiceBowlMod;
import com.rabimimi.nicebowl.blocks.NiceBowlBlock;
import com.rabimimi.nicebowl.blocks.NiceBowlBlockEntity;
import com.rabimimi.nicebowl.utils.Constants;

import net.minecraft.util.Identifier;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class NiceBowlComponentProvider implements IBlockComponentProvider {
  public static final NiceBowlComponentProvider INSTANCE = new NiceBowlComponentProvider();

  private NiceBowlComponentProvider() {
  }

  @Override
  public Identifier getUid() {
    return Constants.JADE_UID_NICEBOWL_BLOCK;
  }

  @Override
  public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig config) {
    if (!(blockAccessor.getBlockEntity() instanceof NiceBowlBlockEntity entity)) {
      NiceBowlMod.LOGGER.error("NiceBowl Jade plugin is applied to a non-NiceBowl block: {}",
          blockAccessor.getBlock());
      return;
    }
    NiceBowlBlock.getTooltipText(entity.getPlayerData()).ifPresent(tooltip::add);
  }

}
