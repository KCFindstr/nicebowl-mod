package com.rabimimi.nicebowl.neoforge;

import com.rabimimi.nicebowl.NiceBowlMod;
import com.rabimimi.nicebowl.events.PlayerEventHandler;

import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;

@Mod(NiceBowlMod.MOD_ID)
public final class NiceBowlModNeoForge {
  public NiceBowlModNeoForge() {
    NiceBowlMod.init();
    NeoForge.EVENT_BUS.addListener(NiceBowlModNeoForge::onItemEntityPickup);
  }

  private static void onItemEntityPickup(ItemEntityPickupEvent.Post event) {
    PlayerEventHandler.onItemPickupPost(event.getPlayer(), event.getItemEntity(), event.getOriginalStack());
  }
}
