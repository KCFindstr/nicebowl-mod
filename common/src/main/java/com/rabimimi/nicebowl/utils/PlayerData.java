package com.rabimimi.nicebowl.utils;

import java.util.Optional;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.rabimimi.nicebowl.items.JuiceBucket;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public record PlayerData(@Nullable UUID uuid, String name) {
  // #region Static fields
  private static final String NAME_NONE = "nicebowl:none";

  public static final PlayerData EMPTY = new PlayerData(null, NAME_NONE);

  @NotNull
  public static PlayerData from(PlayerEntity player) {
    return new PlayerData(player.getUuid(), player.getDisplayName().getString());
  }

  public static Optional<PlayerData> from(@Nullable NbtCompound tag) {
    return Optional.ofNullable(tag).flatMap(t -> {
      if (t.contains(Constants.NBT_KEY_PLAYER_NAME)) {
        return Optional.of(new PlayerData(
            t.contains(Constants.NBT_KEY_PLAYER_UID) ? t.getUuid(Constants.NBT_KEY_PLAYER_UID) : null,
            t.getString(Constants.NBT_KEY_PLAYER_NAME)));
      } else {
        return Optional.empty();
      }
    });
  }

  public static void saveTo(Optional<PlayerData> playerData, @Nullable NbtCompound tag) {
    if (tag == null)
      return;

    if (playerData.isPresent()) {
      if (playerData.get().uuid() == null) {
        tag.remove(Constants.NBT_KEY_PLAYER_UID);
      } else {
        tag.putUuid(Constants.NBT_KEY_PLAYER_UID, playerData.get().uuid());
      }
      tag.putString(Constants.NBT_KEY_PLAYER_NAME, playerData.get().name());
    } else {
      tag.remove(Constants.NBT_KEY_PLAYER_UID);
      tag.remove(Constants.NBT_KEY_PLAYER_NAME);
    }
  }
  // #endregion Static fields

  // #region IContainer

  public static interface IContainer {
    Optional<PlayerData> getPlayerData();

    void setPlayerData(Optional<PlayerData> playerData);

    default void setPlayerData(@NotNull PlayerData playerData) {
      setPlayerData(Optional.of(playerData));
    }

    default void clearPlayerData() {
      setPlayerData(Optional.empty());
    }

    /**
     * @return true if the container has player data, including empty player,
     *         which will be displayed as unknown player.
     */
    default boolean hasPlayerData() {
      return getPlayerData().isPresent();
    }

    /**
     * @return true if the container has valid non-empty player data.
     */
    default boolean hasAssociatedPlayer() {
      return getPlayerData().map(p -> !p.isEmpty()).orElse(false);
    }

    default void copyPlayerDataTo(@Nullable IContainer other) {
      if (other != null) {
        other.setPlayerData(getPlayerData());
      }
    }
  }

  public static class ItemStackContainer implements IContainer {
    private final ItemStack stack;

    public ItemStackContainer(ItemStack stack) {
      this.stack = stack;
    }

    @Override
    public Optional<PlayerData> getPlayerData() {
      return from(stack.getNbt())
          .or(() -> stack.getItem() instanceof JuiceBucket ? Optional.of(EMPTY) : Optional.empty());
    }

    @Override
    public void setPlayerData(Optional<PlayerData> playerData) {
      NbtCompound tag = stack.getOrCreateNbt();
      saveTo(playerData, tag);
      stack.setNbt(tag);
    }

    @Override
    public boolean hasPlayerData() {
      if (stack.getItem() instanceof JuiceBucket)
        return true;
      return Optional.ofNullable(stack.getNbt()).map(
          tag -> tag.contains(Constants.NBT_KEY_PLAYER_NAME)).orElse(false);
    }

    @Override
    public boolean hasAssociatedPlayer() {
      return Optional.ofNullable(stack.getNbt()).map(
          tag -> tag.contains(Constants.NBT_KEY_PLAYER_UID)).orElse(false);
    }
  }

  public static ItemStackContainer container(ItemStack stack) {
    return new ItemStackContainer(stack);
  }
  // #endregion IContainer

  // #region Members
  public boolean isEmpty() {
    return uuid == null && name.equals(NAME_NONE);
  }
  // #endregion Members
}
