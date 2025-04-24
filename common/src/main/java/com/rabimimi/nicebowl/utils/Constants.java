package com.rabimimi.nicebowl.utils;

import com.rabimimi.nicebowl.NiceBowlMod;

import net.minecraft.util.Identifier;

public class Constants {
  public static final String NBT_KEY_PLAYER_UID = NiceBowlMod.MOD_ID + ":player_uid";
  public static final String NBT_KEY_PLAYER_NAME = NiceBowlMod.MOD_ID + ":player_name";
  public static final String TOOLTIP_NICEBOWL_NONE = "tooltip.nicebowl.none";
  public static final String TOOLTIP_NICEBOWL_PLAYER = "tooltip.nicebowl.player";
  public static final int JUICE_COLOR_TINT = 0x7fff99d8;
  public static final int ESTRUS_COLOR_INT = 0xff99d8;
  public static final int DEFAULT_AND_RERENDER = 11;
  public static final Identifier JADE_UID_NICEBOWL_BLOCK = NiceBowlMod.id("nicebowl_block");
}
