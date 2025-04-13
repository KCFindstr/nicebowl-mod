package com.rabimimi.nicebowl.events;

public class EventRegistry {
  public static void init() {
    BucketEventHandler.init();
    PlayerEventHandler.init();
  }
}
