package com.kcfindstr.nicebowl.items;

import java.util.function.Supplier;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

public enum NicebowlArmorMaterial implements IArmorMaterial {
  NICEBOWL("nicebowl", 20, new int[]{2, 5, 6, 2}, 20, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.2F, () -> {
    return Ingredient.of(Items.WHITE_WOOL);
  });

  private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
  private final String name;
  private final int durabilityMultiplier;
  private final int[] slotProtections;
  private final int enchantability;
  private final SoundEvent soundEvent;
  private final float toughness;
  private final float knockbackResistance;
  private final LazyValue<Ingredient> repairMaterial;

  NicebowlArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial) {
    this.name = name;
    this.durabilityMultiplier = maxDamageFactor;
    this.slotProtections = damageReductionAmountArray;
    this.enchantability = enchantability;
    this.soundEvent = soundEvent;
    this.toughness = toughness;
    this.knockbackResistance = knockbackResistance;
    this.repairMaterial = new LazyValue<>(repairMaterial);
  }

  @Override
  public int getDurabilityForSlot(EquipmentSlotType slotIn) {
    return HEALTH_PER_SLOT[slotIn.getIndex()] * this.durabilityMultiplier;
  }

  @Override
  public int getDefenseForSlot(EquipmentSlotType slotIn) {
    return this.slotProtections[slotIn.getIndex()];
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