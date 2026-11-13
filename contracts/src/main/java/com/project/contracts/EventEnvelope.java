package com.project.contracts;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record EventEnvelope<T>(UUID eventId, String type, int version,
                               Instant occurredAt, UUID correlationId, T data) {

    public EventEnvelope {
        Objects.requireNonNull(eventId, "eventId");
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(occurredAt, "occurredAt");
        Objects.requireNonNull(correlationId, "correlationId");
        Objects.requireNonNull(data, "data");
        if (version < 1) {
            throw new IllegalArgumentException("version must be >= 1");
        }
    }

    public static <T> EventEnvelope<T> of(EventType type, UUID correlationId, T data) {
        return new EventEnvelope<>(UUID.randomUUID(), type.getWireName(), 1,
                Instant.now(), correlationId, data);
    }
}