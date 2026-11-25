package com.project.platform;

import org.slf4j.MDC;

import java.util.UUID;

public final class CorrelationId {
    public static final String HEADER = "X-Correlation-Id";
    public static final String MDC_KEY = "correlationId";

    private CorrelationId() {

    }

    public static UUID parseOrNew(String value) {
        if (value != null && value.length() == 36) {
            try {
                UUID parsed = UUID.fromString(value);
                if (parsed.toString().equalsIgnoreCase(value)) {
                    return parsed;
                }
            } catch (IllegalArgumentException ignored) {

            }
        }
        return UUID.randomUUID();
    }

    public static UUID currentOrNew() {
        return parseOrNew((MDC.get(MDC_KEY)));
    }
}

