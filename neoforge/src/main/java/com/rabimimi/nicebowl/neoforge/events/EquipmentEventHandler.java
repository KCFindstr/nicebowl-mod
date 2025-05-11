package com.rabimimi.nicebowl.neoforge.events;

import com.rabimimi.nicebowl.NiceBowlMod;
import com.rabimimi.nicebowl.items.NiceBowl;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = NiceBowlMod.MOD_ID)
public class EquipmentEventHandler {
  @SubscribeEvent
  public static void onEquipmentChanged(LivingEquipmentChangeEvent event) {
    ItemStack stack = event.getTo();
    if (!(stack.getItem() instanceof NiceBowl niceBowl)) {
      return;
    }
    ItemStack newStack = niceBowl.getEquipable(event.getSlot(), stack);
    if (newStack == null) {
      return;
    }
    event.getEntity().equipStack(event.getSlot(), newStack);
    if (event.getEntity() instanceof PlayerEntity player) {
      player.getInventory().markDirty();
    }
  }
}
