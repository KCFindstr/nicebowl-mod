package com.rabimimi.nicebowl.events;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RabiEvent<T> {
  private final List<Consumer<T>> listeners = new ArrayList<>();

  public void register(Consumer<T> listener) {
    listeners.add(listener);
  }

  public void invoke(T event) {
    for (Consumer<T> listener : listeners) {
      listener.accept(event);
    }
  }

  public void unregister(Consumer<T> listener) {
    listeners.remove(listener);
  }
}
