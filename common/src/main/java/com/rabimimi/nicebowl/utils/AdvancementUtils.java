package com.rabimimi.nicebowl.utils;

import com.rabimimi.nicebowl.NiceBowlMod;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class AdvancementUtils {
  public static final Identifier RECEIVE_NICEBOWL = NiceBowlMod.id("actions/receive_nicebowl");
  public static final Identifier SEND_NICEBOWL = NiceBowlMod.id("actions/send_nicebowl");
  public static final Identifier BLOCK_PROJECTILE = NiceBowlMod.id("actions/block_projectile");

  public static void grantAdvancement(ServerPlayerEntity player, Identifier advancementLoc) {
    var advLoader = player.getServer().getAdvancementLoader();
    if (advLoader == null) {
      return;
    }
    Advancement advancement = advLoader.get(advancementLoc);
    if (advancement == null) {
      return;
    }
    PlayerAdvancementTracker playerAdvancements = player.getAdvancementTracker();
    if (playerAdvancements == null) {
      return;
    }
    AdvancementProgress progress = playerAdvancements.getProgress(advancement);
    if (progress.isDone()) {
      return;
    }
    for (String criterion : progress.getUnobtainedCriteria()) {
      playerAdvancements.grantCriterion(advancement, criterion);
    }
  }
}
