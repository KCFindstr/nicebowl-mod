package com.kcfindstr.nicebowl.items;

import java.util.function.Supplier;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

public enum NicebowlArmorMaterial implements IArmorMaterial {
  NICEBOWL("nicebowl", 40, new int[]{5, 8, 10, 5}, 20, SoundEvents.ARMOR_EQUIP_GENERIC, 2.0F, 0.0F, () -> {
    return Ingredient.of(ItemRegistry.niceBowl.get());
  }),
  NICEBOWL_HEAD("nicebowl_head", 40, new int[]{5, 8, 10, 5}, 20, SoundEvents.ARMOR_EQUIP_GENERIC, 2.0F, 0.0F, () -> {
    return Ingredient.of(ItemRegistry.niceBowlHead.get());
  });

  private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
  private final String name;
  private final int maxDamageFactor;
  private final int[] damageReductionAmountArray;
  private final int enchantability;
  private final SoundEvent soundEvent;
  private final float toughness;
  private final float knockbackResistance;
  private final LazyValue<Ingredient> repairMaterial;

  NicebowlArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial) {
      this.name = name;
      this.maxDamageFactor = maxDamageFactor;
      this.damageReductionAmountArray = damageReductionAmountArray;
      this.enchantability = enchantability;
      this.soundEvent = soundEvent;
      this.toughness = toughness;
      this.knockbackResistance = knockbackResistance;
      this.repairMaterial = new LazyValue<>(repairMaterial);
  }

  @Override
  public int getDurabilityForSlot(EquipmentSlotType slotIn) {
      return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
  }

  @Override
  public int getDefenseForSlot(EquipmentSlotType slotIn) {
      return this.damageReductionAmountArray[slotIn.getIndex()];
  }

  @Override
  public int getEnchantmentValue() {
      return this.enchantability;
  }

  @Override
  public SoundEvent getEquipSound() {
      return this.soundEvent;
  }

  @Override
  public Ingredient getRepairIngredient() {
      return this.repairMaterial.get();
  }

  @OnlyIn(Dist.CLIENT)
  public String getName() {
      return this.name;
  }

  @Override
  public float getToughness() {
      return this.toughness;
  }

  @Override
  public float getKnockbackResistance() {
      return this.knockbackResistance;
  }
}