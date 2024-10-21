package me.quickscythe.fluxcore.api.logger;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger("FluxCore");

    public static Logger getLogger(){
        return LOGGER;
    }
}
