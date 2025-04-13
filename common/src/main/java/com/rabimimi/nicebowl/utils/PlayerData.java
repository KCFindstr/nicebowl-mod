package com.rabimimi.nicebowl.utils;

import java.util.UUID;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class PlayerData {
  private static final String NAME_NONE = "nicebowl:none";

  public static final PlayerData EMPTY = new PlayerData();

  public final UUID uid;
  public final String name;

  private PlayerData() {
    this.uid = null;
    this.name = null;
  }

  public PlayerData(PlayerEntity player) {
    this.uid = player.getUuid();
    this.name = player.getDisplayName().getString();
  }

  public PlayerData(NbtCompound tag) {
    if (isValid(tag)) {
      this.uid = tag.getUuid(Constants.NBT_KEY_PLAYER_UID);
      this.name = tag.getString(Constants.NBT_KEY_PLAYER_NAME);
    } else {
      this.uid = null;
      this.name = null;
    }
  }

  public boolean isEmpty() {
    return uid == null || name == null;
  }

  public void saveTo(NbtCompound tag) {
    if (tag == null)
      return;
    if (name == null) {
      tag.remove(Constants.NBT_KEY_PLAYER_UID);
      tag.putString(Constants.NBT_KEY_PLAYER_NAME, NAME_NONE);
    } else {
      tag.putString(Constants.NBT_KEY_PLAYER_NAME, name);
      tag.putUuid(Constants.NBT_KEY_PLAYER_UID, uid);
    }
  }

  private static boolean isValid(NbtCompound tag) {
    return tag != null
        && tag.contains(Constants.NBT_KEY_PLAYER_UID)
        && tag.contains(Constants.NBT_KEY_PLAYER_NAME);
  }

  public static void clear(NbtCompound tag) {
    tag.remove(Constants.NBT_KEY_PLAYER_NAME);
    tag.remove(Constants.NBT_KEY_PLAYER_UID);
  }
}
