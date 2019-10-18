package com.util;

import java.util.logging.ConsoleHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.Level;

public class LoggerHandler {
  private static Logger loggerObject = null;
  private String referenceToClass;

  public LoggerHandler(String referenceToClass) {
    this.referenceToClass = referenceToClass;
  }

  public Logger getGenericConsoleLogger() {
    LogManager.getLogManager().reset();
    LoggerFormatter format = new LoggerFormatter();
    ConsoleHandler console = new ConsoleHandler();

    loggerObject = Logger.getLogger(referenceToClass);

    console.setLevel(Level.ALL);
    console.setFormatter(format);
    loggerObject.addHandler(console);
    return loggerObject;
  }
}
