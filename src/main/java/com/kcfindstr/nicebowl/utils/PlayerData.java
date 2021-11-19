package com.kcfindstr.nicebowl.utils;

import net.minecraft.nbt.CompoundNBT;

public class PlayerData {
  public final String uid;
  public final String name;

  public PlayerData(String uid, String playerName) {
    this.uid = uid;
    this.name = playerName;
  }

  public PlayerData(CompoundNBT tag) {
    if (isValid(tag)) {
      this.uid = tag.getString(Constants.NBT_KEY_PLAYER_UID);
      this.name = tag.getString(Constants.NBT_KEY_PLAYER_NAME);
    } else {
      this.uid = null;
      this.name = null;
    }
  }

  public boolean isValid() {
    return uid != null && name != null;
  }

  public void saveTo(CompoundNBT tag) {
    if (tag != null && isValid()) {
      tag.putString(Constants.NBT_KEY_PLAYER_UID, uid);
      tag.putString(Constants.NBT_KEY_PLAYER_NAME, name);
    }
  }

  public static boolean isValid(CompoundNBT tag) {
    return tag != null
      && tag.contains(Constants.NBT_KEY_PLAYER_UID)
      && tag.contains(Constants.NBT_KEY_PLAYER_NAME);
  }

  public static void clear(CompoundNBT tag) {
    tag.remove(Constants.NBT_KEY_PLAYER_NAME);
    tag.remove(Constants.NBT_KEY_PLAYER_UID);
  }
}
