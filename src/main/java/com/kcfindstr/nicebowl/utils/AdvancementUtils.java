package com.kcfindstr.nicebowl.utils;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;

public class AdvancementUtils {
  public static final ResourceLocation RECEIVE_NICEBOWL = new ResourceLocation(Constants.MOD_ID, "actions/receive_nicebowl");
  public static final ResourceLocation SEND_NICEBOWL = new ResourceLocation(Constants.MOD_ID, "actions/send_nicebowl");

  public static void grantAdvancement(ServerPlayerEntity player, ResourceLocation advancementLoc) {
    AdvancementManager manager = player.getServer().getAdvancements();
    if (manager == null) {
      return;
    }
    Advancement advancement = manager.getAdvancement(advancementLoc);
    if (advancement == null) {
      return;
    }
    PlayerAdvancements playerAdvancements = player.getAdvancements();
    if (playerAdvancements == null) {
      return;
    }
    AdvancementProgress progress = playerAdvancements.getOrStartProgress(advancement);
    if (progress.isDone()) {
      return;
    }
    for (String criterion : progress.getRemainingCriteria()) {
      playerAdvancements.award(advancement, criterion);
    }
  }
}
