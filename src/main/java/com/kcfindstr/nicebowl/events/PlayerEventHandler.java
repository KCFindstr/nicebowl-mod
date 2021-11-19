package com.kcfindstr.nicebowl.events;

import com.kcfindstr.nicebowl.items.ItemRegistry;
import com.kcfindstr.nicebowl.items.NiceBowl;
import com.kcfindstr.nicebowl.utils.Constants;
import com.kcfindstr.nicebowl.utils.PlayerData;
import com.kcfindstr.nicebowl.utils.PlayerUtils;
import com.kcfindstr.nicebowl.utils.Utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
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
      return;
    }
    if (event.wakeImmediately()) {
      return;
    }
    boolean successfulSleep = player.level.getDayTime() == 24000;
    if (!successfulSleep) {
      return;
    }
    ItemStack itemStack = player.getItemBySlot(EquipmentSlotType.LEGS);
    if (itemStack.getItem() == ItemRegistry.niceBowl.get()) {
      PlayerData playerData = new PlayerData(player.getStringUUID(), player.getDisplayName().getString());
      PlayerUtils.setPlayer(itemStack, playerData);
      player.inventory.setChanged();
    }
  }
}
