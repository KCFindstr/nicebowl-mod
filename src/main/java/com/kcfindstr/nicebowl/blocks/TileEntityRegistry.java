package com.kcfindstr.nicebowl.blocks;

import com.kcfindstr.nicebowl.utils.Constants;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityRegistry {
  public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Constants.MOD_ID);
  public static final RegistryObject<TileEntityType<NiceBowlTileEntity>> niceBowlTileEntity
    = TILE_ENTITIES.register("nicebowl_block_tileentity",
      () -> TileEntityType.Builder.of(NiceBowlTileEntity::new,
      BlockRegistry.niceBowl.get()).build(null));
  public static final RegistryObject<TileEntityType<NiceBowlTileEntity>> juiceTileEntity
    = TILE_ENTITIES.register("juice_block_tileentity",
      () -> TileEntityType.Builder.of(NiceBowlTileEntity::new,
      BlockRegistry.juice.get()).build(null));
}
