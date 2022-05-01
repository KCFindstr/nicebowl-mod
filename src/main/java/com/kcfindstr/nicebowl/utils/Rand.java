package com.kcfindstr.nicebowl.utils;

import java.util.Random;

public class Rand {
  public static Random instance = new Random();

  public static boolean withProb(int numerator, int divisor) {
    return instance.nextInt(divisor) < numerator;
  }
}
