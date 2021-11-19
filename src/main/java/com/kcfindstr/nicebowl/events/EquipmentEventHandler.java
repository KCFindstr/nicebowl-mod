package com.kcfindstr.nicebowl.events;

import com.kcfindstr.nicebowl.items.ItemRegistry;
import com.kcfindstr.nicebowl.utils.Constants;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class EquipmentEventHandler {
  private static void changeTo(LivingEquipmentChangeEvent event, ItemStack old, ItemStack now) {
    now.setTag(old.getTag());
    LivingEntity entity = event.getEntityLiving();
    entity.setItemSlot(event.getSlot(), now);
    if (entity instanceof PlayerEntity) {
      PlayerEntity player = (PlayerEntity) entity;
      player.inventory.setChanged();
    }
  }

  @SubscribeEvent
  public static void onEquipmentChanged(LivingEquipmentChangeEvent event) {
    if (event.getEntityLiving().level.isClientSide) {
      return;
    }
    ItemStack stack = event.getTo();
    if (stack.getItem() == ItemRegistry.niceBowl.get()) {
      if (event.getSlot() == EquipmentSlotType.HEAD) {
        ItemStack newStack = new ItemStack(ItemRegistry.niceBowlHead.get());
        changeTo(event, stack, newStack);
      }
    } else if (stack.getItem() == ItemRegistry.niceBowlHead.get()) {
      if (event.getSlot() == EquipmentSlotType.LEGS) {
        ItemStack newStack = new ItemStack(ItemRegistry.niceBowl.get());
        changeTo(event, stack, newStack);
      }
    }
  }
}
