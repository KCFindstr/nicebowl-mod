package com.rabimimi.nicebowl.blocks;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.rabimimi.nicebowl.NiceBowlMod;
import com.rabimimi.nicebowl.items.ItemRegistry;
import com.rabimimi.nicebowl.utils.PlayerData;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NiceBowlBlockEntity extends BlockEntity implements PlayerData.IContainer {
  private static final String NBT_KEY_BOWL_STACK = NiceBowlMod.MOD_ID + ":bowl_stack";
  private static final ItemStack DEFAULT_DROP = new ItemStack(ItemRegistry.NICE_BOWL.get(), 1);

  private Optional<PlayerData> playerData = Optional.empty();
  private ItemStack bowlStack = DEFAULT_DROP.copy();

  private void setBowl(@Nullable NbtCompound tag) {
    if (tag == null || !tag.contains(NBT_KEY_BOWL_STACK)) {
      bowlStack = DEFAULT_DROP.copy();
    } else {
      bowlStack = ItemStack.fromNbt(tag.getCompound(NBT_KEY_BOWL_STACK));
    }
  }

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
    setBowl(tag);
    setPlayerData(PlayerData.from(tag));
  }

  @Override
  protected void writeNbt(NbtCompound tag) {
    super.writeNbt(tag);
    PlayerData.saveTo(playerData, tag);
    if (bowlStack != null) {
      NbtCompound stackTag = new NbtCompound();
      bowlStack.writeNbt(stackTag);
      tag.put(NBT_KEY_BOWL_STACK, stackTag);
    } else {
      tag.remove(NBT_KEY_BOWL_STACK);
    }
  }

  public void setBowlStack(ItemStack stack) {
    if (stack.isEmpty()) {
      bowlStack = DEFAULT_DROP.copy();
    } else {
      bowlStack = stack.copy();
    }
    markDirty();
  }

  // #region PlayerData.IContainer
  private void syncBlockState() {
    World world = getWorld();
    if (world != null && !world.isClient) {
      BlockState state = getCachedState();
      if (!state.contains(NiceBowlBlock.LEVEL)) {
        return;
      }
      int expectedLevel = playerData.isPresent() ? 1 : 0;
      if (state.get(NiceBowlBlock.LEVEL) != expectedLevel) {
        world.setBlockState(pos,
            state.with(NiceBowlBlock.LEVEL, expectedLevel),
            Block.NOTIFY_LISTENERS);
      }
    }
  }

  @Override
  public void setPlayerData(Optional<PlayerData> playerData) {
    this.playerData = playerData;
    syncBlockState();
    markDirty();
  }

  @Override
  public void setWorld(World world) {
    super.setWorld(world);
    syncBlockState();
  }

  @Override
  public Optional<PlayerData> getPlayerData() {
    return playerData;
  }
  // #endregion PlayerData.IContainer

  public ItemStack toItemStack() {
    copyPlayerDataTo(PlayerData.container(bowlStack));
    return bowlStack;
  }
}
