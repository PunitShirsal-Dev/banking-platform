package com.bank.accountservice.shared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.logstash.logback.argument.StructuredArguments;

public class JsonLogger {
    private final Logger logger;

    public JsonLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public void info(String message, Object... arguments) {
        logger.info(message, arguments);
    }

    public void event(String eventType, Object payload) {
        logger.info("eventType={}, payload={}",
                StructuredArguments.keyValue("eventType", eventType),
                StructuredArguments.keyValue("payload", payload));
    }
}