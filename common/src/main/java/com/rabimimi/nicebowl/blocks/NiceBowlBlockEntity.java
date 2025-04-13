package com.rabimimi.nicebowl.blocks;

import org.jetbrains.annotations.Nullable;

import com.rabimimi.nicebowl.utils.PlayerData;
import com.rabimimi.nicebowl.utils.PlayerUtils;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;

public class NiceBowlBlockEntity extends BlockEntity {
  private PlayerData player;

  public NiceBowlBlockEntity(BlockPos pos, BlockState state) {
    super(BlockEntityRegistry.NICE_BOWL_BLOCK_ENTITY.get(), pos, state);
  }

  public void setPlayer(PlayerData player) {
    this.player = player;
    markDirty();
  }

  @Nullable
  public PlayerData getPlayer() {
    return player;
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
    setPlayer(PlayerUtils.getPlayer(tag));
  }

  @Override
  protected void writeNbt(NbtCompound tag) {
    super.writeNbt(tag);
    PlayerUtils.setPlayer(tag, player);
  }
}
