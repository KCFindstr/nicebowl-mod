package com.rabimimi.nicebowl.potions;

import com.rabimimi.nicebowl.utils.Constants;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.random.Random;

public class EstrusEffect extends StatusEffect {
  public static final int EFFECT_INTERVAL = 60;

  public EstrusEffect() {
    super(StatusEffectCategory.BENEFICIAL, Constants.ESTRUS_COLOR_INT);
  }

  private int getEffectInterval(int amplifier) {
    return EFFECT_INTERVAL >> amplifier;
  }

  public boolean tryHeal(Random random, int amplifier) {
    return random.nextInt(5) <= amplifier;
  }

  @Override
  public boolean canApplyUpdateEffect(int duration, int amplifier) {
    int k = getEffectInterval(amplifier);
    if (k > 0) {
      return duration % k == 0;
    } else {
      return true;
    }
  }

  @Override
  public void applyUpdateEffect(LivingEntity entity, int amplifier) {
    float remainHealth = entity.getMaxHealth() - entity.getHealth();
    if (remainHealth <= 0) {
      return;
    }
    entity.heal(0.5f);
  }
}
