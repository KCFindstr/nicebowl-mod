package com.kcfindstr.nicebowl.events;

import com.kcfindstr.nicebowl.items.NiceBowl;
import com.kcfindstr.nicebowl.utils.Constants;
import com.kcfindstr.nicebowl.utils.PlayerUtils;
import com.kcfindstr.nicebowl.utils.Utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class PlayerEventHandler {
  @SubscribeEvent
  public static void onWakeUp(PlayerWakeUpEvent event) {
    PlayerEntity player = event.getPlayer();
    if (player.level.isClientSide) {
      Utils.logInfo("Client side, skipped");
      return;
    }
    Utils.logInfo("[SERVER] Woke up?");
    if (event.wakeImmediately()) {
      return;
    }
    boolean successfulSleep = player.level.getDayTime() == 24000;
    if (!successfulSleep) {
      return;
    }
    Utils.logInfo("[SERVER] Woke up!");
    for (ItemStack itemStack : player.getArmorSlots()) {
      if (itemStack.getItem() instanceof NiceBowl) {
        System.out.println("NiceBowl found");
        PlayerUtils.setPlayer(itemStack, player.getDisplayName().getString());
        player.inventory.setChanged();
      }
    }
  }
}
