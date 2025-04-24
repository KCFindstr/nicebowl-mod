package com.rabimimi.nicebowl.forge.jade;

import com.rabimimi.nicebowl.blocks.NiceBowlBlock;

import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class NiceBowlPlugin implements IWailaPlugin {

  @Override
  public void register(IWailaCommonRegistration registration) {
  }

  @Override
  public void registerClient(IWailaClientRegistration registration) {
    registration.registerBlockComponent(NiceBowlComponentProvider.INSTANCE, NiceBowlBlock.class);
  }
}
