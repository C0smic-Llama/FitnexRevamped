package com.project.platform;

import com.project.contracts.EventEnvelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.function.Consumer;

public final class EventHandlerSupport {
    private static final Logger log = LoggerFactory.getLogger(EventHandlerSupport.class);

    private EventHandlerSupport(){}

    public static <T> void handle(EventEnvelope<T> envelope, Consumer<T> handler){
        String correlationId = envelope.correlationId().toString();
        MDC.put(CorrelationId.MDC_KEY, correlationId);

        try{
            log.info("Handling {} eventId={}", envelope.type(),envelope.eventId());
            handler.accept(envelope.data());
            log.info("Handler returned for {} eventId = {}",envelope.type(), envelope.eventId());
        }catch (RuntimeException ex){
            log.error("Handler failer for {} eventId={} correlationId={}",envelope.type(), envelope.eventId(),correlationId,ex);
            throw ex;
        }finally {
            MDC.remove(CorrelationId.MDC_KEY);
        }
    }
}
