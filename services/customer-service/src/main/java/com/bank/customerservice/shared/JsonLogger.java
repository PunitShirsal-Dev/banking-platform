package com.bank.customerservice.shared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonLogger {

    public static JsonLogger of(Class<?> clazz) {
        return new JsonLogger(LoggerFactory.getLogger(clazz));
    }

    private final Logger logger;

    private JsonLogger(Logger logger) {
        this.logger = logger;
    }

    public void info(String message, Object... arguments) {
        logger.info(message, arguments);
    }

    public void event(String eventType, Object payload) {
        logger.atInfo()
                .setMessage("Event")
                .addKeyValue("eventType", eventType)
                .addKeyValue("payload", payload)
                .log();
    }
}