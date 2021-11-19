package com.kcfindstr.nicebowl.utils;

import com.kcfindstr.nicebowl.blocks.NiceBowlTileEntity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class PlayerUtils {

  public static boolean isValid(PlayerData player) {
    return player != null && player.isValid();
  }

  public static void setPlayer(CompoundNBT tag, PlayerData player) {
    if (tag == null) {
      return;
    }
    if (player == null) {
      PlayerData.clear(tag);
    } else {
      player.saveTo(tag);
    }
  }

  public static void setPlayer(ItemStack stack, PlayerData player) {
    if (stack == null) {
      return;
    }
    CompoundNBT tag = stack.getOrCreateTag();
    setPlayer(tag, player);
    stack.setTag(tag);
  }

  public static void setPlayer(NiceBowlTileEntity entity, PlayerData player) {
    if (entity == null) {
      return;
    }
    entity.setPlayer(player);
  }

  public static PlayerData getPlayer(CompoundNBT tag) {
    PlayerData ret = new PlayerData(tag);
    return ret.isValid() ? ret : null;
  }

  public static PlayerData getPlayer(ItemStack stack) {
    if (!stack.hasTag()) {
      return null;
    }
    CompoundNBT tag = stack.getTag();
    return getPlayer(tag);
  }

  public static void copyPlayerData(ItemStack src, ItemStack dest) {
    if (src == null || dest == null) {
      return;
    }
    PlayerData player = getPlayer(src);
    if (!isValid(player)) {
      return;
    }
    CompoundNBT target = dest.getOrCreateTag();
    player.saveTo(target);
    dest.setTag(target);
  }

  public static void copyPlayerData(ItemStack src, NiceBowlTileEntity dest) {
    if (src == null || dest == null)
      return;
    PlayerData player = getPlayer(src);
    if (!isValid(player)) {
      return;
    }
    setPlayer(dest, player);
  }

  public static void copyPlayerData(NiceBowlTileEntity src, NiceBowlTileEntity dest) {
    if (src == null || dest == null || !src.hasPlayer()) {
      return;
    }
    setPlayer(dest, src.getPlayer());
  }

  public static void copyPlayerData(NiceBowlTileEntity src, ItemStack dest) {
    if (src == null || dest == null || !src.hasPlayer()) {
      return;
    }
    setPlayer(dest, src.getPlayer());
  }
}
