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
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NiceBowlBlockEntity extends BlockEntity implements PlayerData.IContainer {
  private static final String NBT_KEY_BOWL_STACK = NiceBowlMod.MOD_ID + ":bowl_stack";
  private static final ItemStack DEFAULT_DROP = new ItemStack(ItemRegistry.NICE_BOWL, 1);

  private Optional<PlayerData> playerData = Optional.empty();
  private ItemStack bowlStack = DEFAULT_DROP.copy();

  private void setBowl(WrapperLookup registryLookup, @Nullable NbtCompound tag) {
    if (tag == null || !tag.contains(NBT_KEY_BOWL_STACK)) {
      bowlStack = DEFAULT_DROP.copy();
    } else {
      bowlStack = ItemStack.fromNbt(registryLookup, tag.getCompound(NBT_KEY_BOWL_STACK)).orElseGet(DEFAULT_DROP::copy);
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
  public NbtCompound toInitialChunkDataNbt(WrapperLookup registryLookup) {
    NbtCompound tag = new NbtCompound();
    writeNbt(tag, registryLookup);
    return tag;
  }

  @Override
  public void readNbt(NbtCompound tag, WrapperLookup registryLookup) {
    super.readNbt(tag, registryLookup);
    setBowl(registryLookup, tag);
    setPlayerData(PlayerData.container(tag).getPlayerData());
  }

  @Override
  protected void writeNbt(NbtCompound tag, WrapperLookup registryLookup) {
    super.writeNbt(tag, registryLookup);
    PlayerData.container(tag).setPlayerData(playerData);
    if (bowlStack != null) {
      NbtCompound stackTag = new NbtCompound();
      bowlStack.encode(registryLookup, stackTag);
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
