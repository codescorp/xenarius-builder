package io.xenarius.utils.logging

import com.cloudbees.groovy.cps.NonCPS

/**
 * LogLevel enum
 */
enum LogLevel implements Serializable {

  ALL(0, 0),
  TRACE(2, 8),
  DEBUG(3, 12),
  INFO(4, 0),
  DEPRECATED(5, 93),
  WARN(6, 202),
  ERROR(7, 5),
  FATAL(8, 9),
  NONE(Integer.MAX_VALUE, 0)


  Integer level
  Integer color
  
  private static final long serialVersionUID = 2L
  
  static COLOR_CODE_PREFIX = "1;38;5;"

  /**
   * 
   * Creates a new <code>LogLevel</code> with the given details
   * 
   * @param level the level
   * @param color the color
   */
  LogLevel(Integer level, Integer color) {
    this.level = level
    this.color = color
  }

  /**
   * 
   * 
   */
  @NonCPS
  static LogLevel fromInteger(Integer value) {
    for (lvl in values()) {
      if (lvl.getLevel() == value) return lvl
    }
    return INFO
  }

  @NonCPS
  static LogLevel fromString(String value) {
    for (lvl in values()) {
      if (lvl.toString().equalsIgnoreCase(value)) return lvl
    }
    return INFO
  }

  @NonCPS
  public String getColorCode() {
    return COLOR_CODE_PREFIX + color.toString()
  }

}