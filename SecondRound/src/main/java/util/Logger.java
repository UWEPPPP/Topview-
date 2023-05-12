package util;

import java.util.logging.Level;

/**
 * 日志记录器
 *
 * @author 刘家辉
 * @date 2023/05/12
 */
public class Logger {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger("Logger");

    /**
     * 记录 INFO 级别日志信息。
     *
     * @param message 日志信息
     */
    public static void info(String message) {
        LOGGER.log(Level.INFO, message);
    }

    /**
     * 记录 WARNING 级别日志信息。
     *
     * @param message 日志信息
     */
    public static void warning(String message) {
        LOGGER.log(Level.WARNING, message);
    }

    /**
     * 记录 SEVERE 级别日志信息。
     *
     * @param message 日志信息
     */
    public static void severe(String message) {
        LOGGER.log(Level.SEVERE, message);
    }

    /**
     * 记录日志信息（自定义日志级别）。
     *
     * @param level   日志级别
     * @param message 日志信息
     */
    public static void log(Level level, String message) {
        LOGGER.log(level, message);
    }

    /**
     * 记录异常信息。
     *
     * @param level     日志级别
     * @param message   日志信息
     * @param exception 异常对象
     */
    public static void logException(Level level, String message, Throwable exception) {
        LOGGER.log(level, message, exception);
    }
}
