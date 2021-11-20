package com.kcfindstr.nicebowl.events;

import com.kcfindstr.nicebowl.items.ItemRegistry;
import com.kcfindstr.nicebowl.items.NiceBowl;
import com.kcfindstr.nicebowl.utils.AdvancementUtils;
import com.kcfindstr.nicebowl.utils.Constants;
import com.kcfindstr.nicebowl.utils.PlayerData;
import com.kcfindstr.nicebowl.utils.PlayerUtils;
import com.kcfindstr.nicebowl.utils.Utils;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
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
    Utils.logInfo(player.getDisplayName().getString() + " wakes up, day time is " + player.level.getDayTime());
    boolean successfulSleep = player.level.getDayTime() == 24000 || player.level.getDayTime() <= 20;
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

  @SubscribeEvent
  public static void onItemPickup(EntityItemPickupEvent event) {
    if (event.getEntity().level.isClientSide || !(event.getPlayer() instanceof ServerPlayerEntity)) {
      return;
    }
    ItemEntity item = event.getItem();
    if (item == null) {
      return;
    }
    ItemStack itemStack = item.getItem();
    if (!(itemStack.getItem() instanceof NiceBowl)) {
      return;
    }
    ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
    ServerPlayerEntity thrower = player.getServer().getPlayerList().getPlayer(item.getThrower());
    if (thrower == null) {
      return;
    }
    Utils.logInfo("Nicebowl thrown by " + thrower.getStringUUID() + " picked up by " + player.getStringUUID());
    if (thrower == null || thrower.getUUID().equals(player.getUUID())) {
      return;
    }
    PlayerData itemOwner = PlayerUtils.getPlayer(itemStack);
    if (!PlayerUtils.isValid(itemOwner)) {
      return;
    }
    Utils.logInfo("Nicebowl is from " + itemOwner.uid);
    if (!itemOwner.uid.equals(thrower.getStringUUID())) {
      return;
    }
    AdvancementUtils.grantAdvancement(player, AdvancementUtils.RECEIVE_NICEBOWL);
    AdvancementUtils.grantAdvancement(thrower, AdvancementUtils.SEND_NICEBOWL);
  }

  @SubscribeEvent
  public static void onLivingHurt(LivingHurtEvent event) {
    LivingEntity entity = event.getEntityLiving();
    if (entity == null || entity.level.isClientSide) {
      return;
    }
    DamageSource source = event.getSource();
    if (source == null || !source.isProjectile()) {
      return;
    }
    int niceBowlCount = 0;
    if (entity.getItemBySlot(EquipmentSlotType.HEAD).getItem() instanceof NiceBowl) {
      niceBowlCount++;
    }
    if (entity.getItemBySlot(EquipmentSlotType.LEGS).getItem() instanceof NiceBowl) {
      niceBowlCount++;
    }
    if (niceBowlCount >= 2) {
      event.setAmount(0);
      if (entity instanceof ServerPlayerEntity) {
        ServerPlayerEntity player = (ServerPlayerEntity) entity;
        AdvancementUtils.grantAdvancement(player, AdvancementUtils.BLOCK_PROJECTILE);
      }
      return;
    }
  }
}
