package com.kcfindstr.nicebowl.blocks;

import javax.annotation.Nullable;

import com.kcfindstr.nicebowl.utils.PlayerData;
import com.kcfindstr.nicebowl.utils.PlayerUtils;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;

public class NiceBowlTileEntity extends TileEntity {
  private PlayerData player;

  public NiceBowlTileEntity() {
    super(TileEntityRegistry.niceBowlTileEntity.get());
  }

  public void setPlayer(PlayerData player) {
    this.player = PlayerUtils.isValid(player) ? player : null;
    setChanged();
  }

  @Nullable
  public PlayerData getPlayer() {
    return player;
  }

  public boolean hasPlayer() {
    return PlayerUtils.isValid(player);
  }

  public String getPlayerName() {
    return player.name;
  }

  private void loadPlayer(CompoundNBT tag) {
    setPlayer(PlayerUtils.getPlayer(tag));
  }
  
  @Nullable
  @Override
  public SUpdateTileEntityPacket getUpdatePacket() {
    return new SUpdateTileEntityPacket(getBlockPos(), -1, getUpdateTag());
  }

  @Override
  public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
    loadPlayer(pkt.getTag());
  }
  
  @Override
  public CompoundNBT getUpdateTag() {
    CompoundNBT tag = super.getUpdateTag();
    save(tag);
    return tag;
  }
  
  @Override
  public void handleUpdateTag(BlockState state, CompoundNBT tag) {
    loadPlayer(tag);
  }
  
  @Override
  public void load(BlockState state, CompoundNBT tag) {
    super.load(state, tag);
    handleUpdateTag(state, tag);
  }

  @Override
  public CompoundNBT save(CompoundNBT tag) {
    PlayerUtils.setPlayer(tag, player);
    return super.save(tag);
  }
}
