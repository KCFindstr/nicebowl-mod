package com.kcfindstr.nicebowl.blocks;

import javax.annotation.Nullable;

import com.kcfindstr.nicebowl.utils.Constants;
import com.kcfindstr.nicebowl.utils.PlayerUtils;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;

public class NiceBowlTileEntity extends TileEntity {
  private String player = "";

  public NiceBowlTileEntity() {
    super(TileEntityRegistry.niceBowlTileEntity.get());
  }

  public void setPlayer(String player) {
    this.player = player;
    setChanged();
  }

  public String getPlayer() {
    return player;
  }

  public boolean hasPlayer() {
    return !PlayerUtils.isNullOrEmpty(player);
  }

  public String getPlayerName() {
    return player;
  }

  private void loadPlayer(CompoundNBT tag) {
    player = tag.getString(Constants.NBT_KEY_PLAYER);
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
    tag.putString(Constants.NBT_KEY_PLAYER, player);
    return super.save(tag);
  }
}
