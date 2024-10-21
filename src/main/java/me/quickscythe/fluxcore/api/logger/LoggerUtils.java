package me.quickscythe.fluxcore.api.logger;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtils {

    private final Logger LOGGER;

    public LoggerUtils() {
        LOGGER = LoggerFactory.getILoggerFactory().getLogger("FluxCore");
    }

    public void log(String message) {
        log(LogLevel.INFORMATION, message);
    }

    public void log(LogLevel level, String message) {
        switch (level) {
            case INFORMATION:
                LOGGER.info(message);
                return;
            case DEBUG:
                LOGGER.debug(message);
                return;
            case ERROR:
                LOGGER.error(message);
                return;
            case TRACE:
                LOGGER.trace(message);
                return;
            case WARNING:
                LOGGER.warn(message);
                return;
            default:
                LOGGER.info(message);

        }
    }

    public Logger getLogger(){
        return LOGGER;
    }

    public enum LogLevel {
        INFORMATION, ERROR, WARNING, DEBUG, TRACE
    }
}
