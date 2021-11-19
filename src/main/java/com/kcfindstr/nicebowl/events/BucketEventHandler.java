package com.kcfindstr.nicebowl.events;

import com.kcfindstr.nicebowl.blocks.NiceBowlBlock;
import com.kcfindstr.nicebowl.blocks.NiceBowlTileEntity;
import com.kcfindstr.nicebowl.items.ItemRegistry;
import com.kcfindstr.nicebowl.utils.Constants;
import com.kcfindstr.nicebowl.utils.PlayerUtils;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class BucketEventHandler {
  @SubscribeEvent
  public static void onBucketUsed(FillBucketEvent event) {
    World world = event.getWorld();
    Vector3d loc = event.getTarget().getLocation();
    BlockPos pos = new BlockPos(loc.x, loc.y, loc.z);
    if (event.getEmptyBucket().getItem() != Items.BUCKET) {
      return;
    }
    BlockState blockState = world.getBlockState(pos);
    if (!(blockState.getBlock() instanceof NiceBowlBlock)) {
      return;
    }
    NiceBowlTileEntity tileEntity = (NiceBowlTileEntity) world.getBlockEntity(pos);
    if (!tileEntity.hasPlayer()) {
      return;
    }
    // Collect juice
    ItemStack itemStack = new ItemStack(ItemRegistry.juiceBucket.get());
    if (!world.isClientSide) {
      PlayerUtils.copyPlayerData(tileEntity, itemStack);
      tileEntity.setPlayer(null);
      event.getPlayer().inventory.setChanged();
      world.sendBlockUpdated(pos, blockState, blockState, 3);
    }
    event.setFilledBucket(itemStack);
    event.setResult(Result.ALLOW);
    world.playSound(event.getPlayer(), pos, SoundEvents.BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
  }
}
