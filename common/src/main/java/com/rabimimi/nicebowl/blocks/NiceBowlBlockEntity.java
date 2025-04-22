package com.rabimimi.nicebowl.blocks;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.rabimimi.nicebowl.items.ItemRegistry;
import com.rabimimi.nicebowl.utils.Constants;
import com.rabimimi.nicebowl.utils.PlayerData;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;

public class NiceBowlBlockEntity extends BlockEntity implements PlayerData.IContainer {
  private Optional<PlayerData> playerData = Optional.empty();

  public NiceBowlBlockEntity(BlockPos pos, BlockState state) {
    super(BlockEntityRegistry.NICE_BOWL_BLOCK_ENTITY.get(), pos, state);
  }

  @Nullable
  @Override
  public Packet<ClientPlayPacketListener> toUpdatePacket() {
    return BlockEntityUpdateS2CPacket.create(this);
  }

  @Override
  public NbtCompound toInitialChunkDataNbt() {
    NbtCompound tag = new NbtCompound();
    writeNbt(tag);
    return tag;
  }

  @Override
  public void readNbt(NbtCompound tag) {
    super.readNbt(tag);
    setPlayerData(PlayerData.from(tag));
  }

  @Override
  protected void writeNbt(NbtCompound tag) {
    super.writeNbt(tag);
    PlayerData.saveTo(playerData, tag);
  }

  // #region PlayerData.IContainer
  public void setPlayerData(Optional<PlayerData> playerData) {
    this.playerData = playerData;
    getWorld().setBlockState(pos,
        getCachedState().with(NiceBowlBlock.LEVEL, playerData.isPresent() ? 1 : 0),
        Constants.DEFAULT_AND_RERENDER);
    markDirty();
  }

  public Optional<PlayerData> getPlayerData() {
    return playerData;
  }

  public ItemStack toItemStack() {
    ItemStack stack = new ItemStack(ItemRegistry.NICE_BOWL.get(), 1);
    copyPlayerDataTo(PlayerData.container(stack));
    return stack;
  }
  // #endregion PlayerData.IContainer
}
