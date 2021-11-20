package com.kcfindstr.nicebowl.items;

import com.kcfindstr.nicebowl.blocks.BlockRegistry;
import com.kcfindstr.nicebowl.utils.Constants;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {
  public static final ItemGroup itemGroup = new NiceBowlGroup();

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
  public static final RegistryObject<ArmorItem> niceBowl = ITEMS.register("nicebowl", NiceBowl::new);
  public static final RegistryObject<ArmorItem> niceBowlHead = ITEMS.register("nicebowl_head", NiceBowlHead::new);
  public static final RegistryObject<BucketItem> juiceBucket = ITEMS.register("juice_bucket", JuiceBucket::new);
  public static final RegistryObject<BlockItem> niceBowlBlock = ITEMS.register("nicebowl_block", () -> {
    return new BlockItem(BlockRegistry.niceBowl.get(), new Item.Properties().tab(itemGroup));
  });
}