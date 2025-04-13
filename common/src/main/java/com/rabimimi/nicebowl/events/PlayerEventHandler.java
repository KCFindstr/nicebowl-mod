package com.rabimimi.nicebowl.events;

import java.util.Optional;

import com.rabimimi.nicebowl.NiceBowlMod;
import com.rabimimi.nicebowl.blocks.JuiceBlock;
import com.rabimimi.nicebowl.items.NiceBowl;
import com.rabimimi.nicebowl.potions.EffectRegistry;
import com.rabimimi.nicebowl.potions.EstrusEffect;
import com.rabimimi.nicebowl.utils.AdvancementUtils;
import com.rabimimi.nicebowl.utils.Constants;
import com.rabimimi.nicebowl.utils.PlayerData;
import com.rabimimi.nicebowl.utils.PlayerUtils;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class PlayerEventHandler {

  public static final RabiEvent<ServerPlayerEntity> PLAYER_WAKE_UP_SKIP_NIGHT = new RabiEvent<>();

  public static void init() {
    PlayerEvent.PICKUP_ITEM_POST.register(PlayerEventHandler::onItemPickupPost);
    TickEvent.PLAYER_PRE.register(PlayerEventHandler::onPlayerTickPre);
    EntityEvent.LIVING_HURT.register(PlayerEventHandler::onLivingHurt);
    PLAYER_WAKE_UP_SKIP_NIGHT.register(PlayerEventHandler::onWakeUpSkipNight);
  }

  private static void onWakeUpSkipNight(ServerPlayerEntity player) {
    NiceBowlMod.LOGGER.info("Player {} woke up", player.getDisplayName().getString());
    ItemStack itemStack = player.getEquippedStack(EquipmentSlot.LEGS);
    if (itemStack.getItem() instanceof NiceBowl) {
      PlayerData playerData = new PlayerData(player);
      PlayerUtils.setPlayer(itemStack, playerData);
      player.getInventory().markDirty();
    }
  }

  private static void onItemPickupPost(PlayerEntity player, ItemEntity itemEntity, ItemStack stack) {
    if (!(player instanceof ServerPlayerEntity serverPlayer)) {
      return;
    }
    if (!(stack.getItem() instanceof NiceBowl)) {
      return;
    }
    if (!(itemEntity.getOwner() instanceof ServerPlayerEntity thrower)) {
      return;
    }
    NiceBowlMod.LOGGER.info("Nicebowl thrown by {} picked up by {}",
        thrower.getDisplayName().getString(),
        player.getDisplayName().getString());
    if (thrower.equals(player)) {
      return;
    }
    PlayerData itemOwner = PlayerUtils.getPlayer(stack);
    if (PlayerUtils.isEmpty(itemOwner)) {
      return;
    }
    NiceBowlMod.LOGGER.info("Nicebowl is from {}" + itemOwner.uid);
    if (!itemOwner.uid.equals(thrower.getUuidAsString())) {
      return;
    }
    AdvancementUtils.grantAdvancement(serverPlayer, AdvancementUtils.RECEIVE_NICEBOWL);
    AdvancementUtils.grantAdvancement(thrower, AdvancementUtils.SEND_NICEBOWL);
  }

  private static EventResult onLivingHurt(LivingEntity entity, DamageSource source, float amount) {
    if (entity.getWorld().isClient
        || !source.isIn(DamageTypeTags.IS_PROJECTILE)
        || amount <= 0) {
      return EventResult.pass();
    }
    EstrusEffect estrus = EffectRegistry.ESTRUS.get();
    StatusEffectInstance effect = entity.getStatusEffect(estrus);
    if (effect != null && estrus.tryHeal(entity.getRandom(), effect.getAmplifier())) {
      entity.heal(amount);
      return EventResult.interruptFalse();
    } else {
      int niceBowlCount = 0;
      for (var item : entity.getArmorItems()) {
        if (item.getItem() instanceof NiceBowl) {
          niceBowlCount++;
        }
      }
      if (niceBowlCount >= 2) {
        if (entity instanceof ServerPlayerEntity serverPlayer) {
          AdvancementUtils.grantAdvancement(serverPlayer, AdvancementUtils.BLOCK_PROJECTILE);
        }
        return EventResult.interruptFalse();
      }
    }
    return EventResult.pass();
  }

  private static void onPlayerTickPre(PlayerEntity player) {
    if (player.getWorld().isClient) {
      return;
    }
    BlockPos pos = player.getBlockPos();
    BlockState blockState = player.getWorld().getBlockState(pos);
    if (blockState.getBlock() instanceof JuiceBlock) {
      StatusEffect effect = EffectRegistry.ESTRUS.get();
      StatusEffectInstance instance = player.getStatusEffect(effect);
      if (instance == null || instance.getDuration() <= EstrusEffect.EFFECT_INTERVAL) {
        player.addStatusEffect(new StatusEffectInstance(effect, EstrusEffect.EFFECT_INTERVAL * 2));
      }
    }
  }

  public static Optional<Integer> getFogColor(PlayerEntity playerEntity) {
    if (playerEntity.hasStatusEffect(EffectRegistry.ESTRUS.get())) {
      return Optional.of(Constants.ESTRUS_COLOR_INT);
    } else {
      return Optional.empty();
    }
  }

  public static Optional<Float> getFogDensity(PlayerEntity playerEntity) {
    StatusEffectInstance effect = playerEntity.getStatusEffect(EffectRegistry.ESTRUS.get());
    if (effect == null) {
      return Optional.empty();
    } else {
      return Optional.of(Math.min((float) effect.getDuration() / 20, 1));
    }
  }
}
