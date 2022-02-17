package io.xenarius.utils.logging

import com.cloudbees.groovy.cps.NonCPS

/**
 * LogLevel enum
 */
enum LogLevel implements Serializable {

  Integer level
  Integer color

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