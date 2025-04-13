package com.rabimimi.nicebowl.utils;

import org.jetbrains.annotations.Nullable;

import com.rabimimi.nicebowl.blocks.NiceBowlBlockEntity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class PlayerUtils {

  public static boolean isEmpty(PlayerData playerData) {
    return playerData == null || playerData.isEmpty();
  }

  public static void setPlayer(NbtCompound tag, PlayerData player) {
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
    NbtCompound tag = stack.getOrCreateNbt();
    setPlayer(tag, player);
    stack.setNbt(tag);
  }

  public static void setPlayer(NiceBowlBlockEntity entity, PlayerData player) {
    if (entity == null) {
      return;
    }
    entity.setPlayer(player);
  }

  @Nullable
  public static PlayerData getPlayer(NbtCompound tag) {
    if (tag == null) {
      return null;
    }
    return new PlayerData(tag);
  }

  @Nullable
  public static PlayerData getPlayer(ItemStack stack) {
    if (!stack.hasNbt()) {
      return null;
    }
    NbtCompound tag = stack.getNbt();
    return getPlayer(tag);
  }

  public static void copyPlayerData(ItemStack src, ItemStack dest) {
    if (src == null || dest == null) {
      return;
    }
    PlayerData player = getPlayer(src);
    if (player == null) {
      return;
    }
    NbtCompound target = dest.getOrCreateNbt();
    player.saveTo(target);
    dest.setNbt(target);
  }

  public static void copyPlayerData(ItemStack src, NiceBowlBlockEntity dest) {
    if (src == null || dest == null)
      return;
    PlayerData player = getPlayer(src);
    setPlayer(dest, player);
  }

  public static void copyPlayerData(NiceBowlBlockEntity src, NiceBowlBlockEntity dest) {
    if (src == null || dest == null) {
      return;
    }
    setPlayer(dest, src.getPlayer());
  }

  public static void copyPlayerData(NiceBowlBlockEntity src, ItemStack dest) {
    if (src == null || dest == null) {
      return;
    }
    setPlayer(dest, src.getPlayer());
  }
}
