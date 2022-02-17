package io.xenarius.utils.logging

import org.jenkinsci.plugins.workflow.cps.DSL

/**
 * The logging functionality for the pipeline script
 */
class Logger implements Serializable {

  private static final long serialVersionUID = 1L

  static DSL dsl
  static Script script
  static LogLevel level = LogLevel.TRACE
  public String name = ""

  public static Boolean initialized = false

  Logger(String name = "") {
    this.name = name
  }

  Logger(Object logScope) {
    if (logScope instanceof Object) {
      this.name = getClassName(logScope)
      if (this.name == null) {
        this.name = "$logScope"
      }
    }
  }

  @NonCPS
  static void init(DSL dsl, LogLevel logLvl = LogLevel.INFO) {
    if (logLvl == null) logLvl = LogLevel.INFO
    level = logLvl
    if (Logger.initialized == true) {
      return
    }
    this.dsl = dsl
    initialized = true
    Logger tmpLogger = new Logger('Logger')
    tmpLogger.deprecated('Logger.init(DSL dsl, logLevel)','Logger.init(Script script, logLevel)')
  }

  @NonCPS
  static void init(Script script, LogLevel logLvl = LogLevel.INFO) {
    if (logLvl == null) logLvl = LogLevel.INFO
    level = logLvl
    if (Logger.initialized == true) {
      return
    }
    this.script = script
    this.dsl = (DSL) script.steps
    initialized = true
  }

  @NonCPS
  static void init(Script script, Map map) {
    LogLevel lvl
    if (map) {
      lvl = map[ConfigConstants.LOGLEVEL] ?: LogLevel.INFO
    } else {
      lvl = LogLevel.INFO
    }
    init(script, lvl)
  }

  @NonCPS
  static void init(Script script, String sLevel) {
    if (sLevel == null) sLevel = LogLevel.INFO
    init(script, LogLevel.fromString(sLevel))
  }

  @NonCPS
  static void init(Script script, Integer iLevel) {
    if (iLevel == null) iLevel = LogLevel.INFO.getLevel()
    init(script, LogLevel.fromInteger(iLevel))
  }

  @NonCPS
  void trace(String message, Object object) {
    log(LogLevel.TRACE, message, object)
  }

  @NonCPS
  void info(String message, Object object) {
    log(LogLevel.INFO, message, object)
  }

  @NonCPS
  void debug(String message, Object object) {
    log(LogLevel.DEBUG, message, object)
  }

  @NonCPS
  void warn(String message, Object object) {
    log(LogLevel.WARN, message, object)
  }

  @NonCPS
  void error(String message, Object object) {
    log(LogLevel.ERROR, message, object)
  }

  @NonCPS
  void fatal(String message, Object object) {
    log(LogLevel.FATAL, message, object)
  }

  @NonCPS
  void trace(String message) {
    log(LogLevel.TRACE, message)
  }

  @NonCPS
  void info(String message) {
    log(LogLevel.INFO, message)
  }

  @NonCPS
  void debug(String message) {
    log(LogLevel.DEBUG, message)
  }

  @NonCPS
  void warn(String message) {
    log(LogLevel.WARN, message)
  }

  @NonCPS
  void error(String message) {
    log(LogLevel.ERROR, message)
  }

  @NonCPS
  void deprecated(String message) {
    try {
      Logger.dsl.addWarningBadge(message)
    } catch (Throwable ex) {
      // no badge plugin available
    }
    log(LogLevel.DEPRECATED, message)
  }

  @NonCPS
  void deprecated(String deprecatedItem, String newItem) {
    String message = "The step/function/class '$deprecatedItem' is marked as depecreated and will be removed in future releases. " +
      "Please use '$newItem' instead."
    deprecated(message)
  }

  @NonCPS
  void fatal(String message) {
    log(LogLevel.FATAL, message)
  }

  @NonCPS
  void log(LogLevel logLevel, String message, Object object) {
    if (doLog(logLevel)) {
      def objectName = getClassName(object)
      if (objectName != null) {
        objectName = "($objectName) "
      } else {
        objectName = ""
      }

      def objectString = object.toString()
      String msg = "$name : $message -> $objectName$objectString"
      writeLogMsg(logLevel, msg)
    }
  }

  @NonCPS
  void log(LogLevel logLevel, String message) {
    if (doLog(logLevel)) {
      String msg = "$name : $message"
      writeLogMsg(logLevel, msg)
    }
  }

  @NonCPS
  private static void writeLogMsg(LogLevel logLevel, String msg) {
    String lvlString = "[${logLevel.toString()}]"

    lvlString = wrapColor(logLevel.getColorCode(), lvlString)

    if (dsl != null) {
      dsl.echo("$lvlString $msg")
    }
  }

  @NonCPS
  private static String wrapColor(String colorCode, String str) {
    String ret = str
    if (hasTermEnv()) {
      ret = "\u001B[${colorCode}m${str}\u001B[0m"
    }
    return ret
  }

  @NonCPS
  private static Boolean hasTermEnv() {
    String termEnv = null
    if (script != null) {
      try {
        termEnv = script.env.TERM
      } catch (Exception ex) {

      }
    }
    return termEnv != null
  }

  @NonCPS
  private static boolean doLog(LogLevel logLevel) {
    if (logLevel.getLevel() >= level.getLevel()) {
      return true
    }
    return false
  }

  @NonCPS
  private static String getClassName(Object object) {
    String objectName = null
    // try to retrieve as much information as possible about the class
    try {
      Class objectClass = object.getClass()
      objectName = objectClass.getName().toString()
      objectName = objectClass.getCanonicalName().toString()
    } catch (RejectedAccessException e) {
      // do nothing
    }

    return objectName
  }
}