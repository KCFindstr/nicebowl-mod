package com.kcfindstr.nicebowl.utils;

import java.util.Collection;
import java.util.function.Predicate;

public class CollectionUtils {
  public static <T> T find(Collection<T> collection, Predicate<T> predicate) {
    for (T t : collection) {
      if (predicate.test(t)) {
        return t;
      }
    }
    return null;
  }
}
