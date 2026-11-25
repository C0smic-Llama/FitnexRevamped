package com.project.platform;


import com.project.contracts.EventEnvelope;
import com.project.contracts.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
public class EventPublisher {

    private static final Logger log = LoggerFactory.getLogger(EventPublisher.class);

    private final ApplicationEventPublisher springPublisher;

    public EventPublisher(ApplicationEventPublisher springPublisher){
        this.springPublisher =springPublisher;
    }

    public<T>void publish(T payload){
        if(!TransactionSynchronizationManager.isActualTransactionActive()){
            throw new IllegalStateException("Events must be published inside a transaction");
        }
        EventType type = EventType.forPayload(payload.getClass());
        EventEnvelope<T> envelope = EventEnvelope.of(type, CorrelationId.currentOrNew(),payload);

        log.info("Publishing {} eventId = {} correlationId = {}", type.getWireName(),envelope.eventId(),envelope.correlationId());

        springPublisher.publishEvent(new LocalEvent<>(envelope));
    }

}
