package com.rabimimi.nicebowl.utils;

import java.util.Optional;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rabimimi.nicebowl.NiceBowlMod;
import com.rabimimi.nicebowl.components.ComponentRegistry;
import com.rabimimi.nicebowl.items.JuiceBucket;

import net.minecraft.component.ComponentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Uuids;

public record PlayerData(Optional<UUID> uuid, String name) {
  // #region Static fields
  private static final String NAME_NONE = "nicebowl:none";

  public static final PlayerData EMPTY = new PlayerData(Optional.empty(), NAME_NONE);

  public static final MapCodec<PlayerData> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
      Uuids.CODEC.optionalFieldOf(Constants.NBT_KEY_PLAYER_UID)
          .forGetter(PlayerData::uuid),
      Codec.STRING.fieldOf(Constants.NBT_KEY_PLAYER_NAME)
          .forGetter(PlayerData::name))
      .apply(instance, PlayerData::new));

  @NotNull
  public static PlayerData from(PlayerEntity player) {
    return new PlayerData(Optional.ofNullable(player.getUuid()), player.getDisplayName().getString());
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
      ComponentType<PlayerData> componentType = ComponentRegistry.PLAYER_DATA_COMPONENT.value();
      if (!stack.contains(componentType))
        return stack.getItem() instanceof JuiceBucket ? Optional.of(EMPTY) : Optional.empty();
      return Optional.of(stack.get(componentType));
    }

    @Override
    public void setPlayerData(Optional<PlayerData> playerData) {
      ComponentType<PlayerData> componentType = ComponentRegistry.PLAYER_DATA_COMPONENT.value();
      if (playerData.isPresent()) {
        stack.set(componentType, playerData.get());
      } else if (stack.contains(componentType)) {
        stack.remove(componentType);
      }
    }

    @Override
    public boolean hasPlayerData() {
      if (stack.getItem() instanceof JuiceBucket)
        return true;
      return stack.contains(ComponentRegistry.PLAYER_DATA_COMPONENT.value());
    }

    @Override
    public boolean hasAssociatedPlayer() {
      ComponentType<PlayerData> componentType = ComponentRegistry.PLAYER_DATA_COMPONENT.value();
      if (!stack.contains(componentType))
        return false;

      return stack.get(componentType).uuid().isPresent();
    }
  }

  public static class NbtContainer implements IContainer {
    private final @Nullable NbtCompound tag;

    public NbtContainer(@Nullable NbtCompound tag) {
      this.tag = tag;
    }

    public @Nullable NbtCompound getTag() {
      return tag;
    }

    @Override
    public Optional<PlayerData> getPlayerData() {
      return Optional.ofNullable(tag).flatMap(t -> {
        if (t.contains(Constants.NBT_KEY_PLAYER_NAME)) {
          return Optional.of(new PlayerData(
              Optional
                  .ofNullable(
                      t.contains(Constants.NBT_KEY_PLAYER_UID) ? t.getUuid(Constants.NBT_KEY_PLAYER_UID) : null),
              t.getString(Constants.NBT_KEY_PLAYER_NAME)));
        } else {
          return Optional.empty();
        }
      });
    }

    @Override
    public void setPlayerData(Optional<PlayerData> playerData) {
      if (tag == null) {
        NiceBowlMod.LOGGER.warn("Attempting to set player data on null tag");
        return;
      }

      if (playerData.isPresent()) {
        if (playerData.get().uuid().isEmpty()) {
          tag.remove(Constants.NBT_KEY_PLAYER_UID);
        } else {
          tag.putUuid(Constants.NBT_KEY_PLAYER_UID, playerData.get().uuid().get());
        }
        tag.putString(Constants.NBT_KEY_PLAYER_NAME, playerData.get().name());
      } else {
        tag.remove(Constants.NBT_KEY_PLAYER_UID);
        tag.remove(Constants.NBT_KEY_PLAYER_NAME);
      }
    }
  }

  public static ItemStackContainer container(ItemStack stack) {
    return new ItemStackContainer(stack);
  }

  public static NbtContainer container(@Nullable NbtCompound tag) {
    return new NbtContainer(tag);
  }
  // #endregion IContainer

  // #region Members
  public boolean isEmpty() {
    return uuid.isEmpty() && name.equals(NAME_NONE);
  }
  // #endregion Members
}
