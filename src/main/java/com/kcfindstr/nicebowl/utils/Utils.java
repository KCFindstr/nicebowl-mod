package com.kcfindstr.nicebowl.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {
  public static Logger LOGGER = LogManager.getLogger(Constants.MOD_ID);

  public static void logInfo(String message) {
    LOGGER.info(message);
  }
}
