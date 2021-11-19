package com.kcfindstr.nicebowl.utils;

import com.kcfindstr.nicebowl.blocks.NiceBowlTileEntity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class PlayerUtils {

  public static boolean isNullOrEmpty(String str) {
    return str == null || str.length() == 0;
  }

  public static void setPlayer(ItemStack stack, String player) {
    if (stack == null) {
      return;
    }
    CompoundNBT tag = new CompoundNBT();
    tag.putString(Constants.NBT_KEY_PLAYER, player);
    stack.setTag(tag);
  }

  public static void setPlayer(NiceBowlTileEntity entity, String player) {
    if (entity == null) {
      return;
    }
    entity.setPlayer(player);
  }

  public static String getPlayer(CompoundNBT tag) {
    if (tag == null) {
      return null;
    }
    if (!tag.contains(Constants.NBT_KEY_PLAYER)) {
      return null;
    }
    String player = tag.getString(Constants.NBT_KEY_PLAYER);
    if (isNullOrEmpty(player)) {
      return null;
    }
    return player;
  }

  public static String getPlayer(ItemStack stack) {
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
    String player = getPlayer(src);
    if (player == null) {
      return;
    }
    CompoundNBT target = dest.getOrCreateTag();
    target.putString(Constants.NBT_KEY_PLAYER, player);
    dest.setTag(target);
  }

  public static void copyPlayerData(ItemStack src, NiceBowlTileEntity dest) {
    if (src == null || dest == null)
      return;
    String player = getPlayer(src);
    if (player == null) {
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
