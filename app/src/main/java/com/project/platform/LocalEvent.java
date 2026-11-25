package com.project.platform;

import com.project.contracts.EventEnvelope;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

public record LocalEvent<T>(EventEnvelope<T> envelope) implements ResolvableTypeProvider {

    @Override
    public ResolvableType getResolvableType() {

        return ResolvableType.forClassWithGenerics(
                LocalEvent.class,
                ResolvableType.forInstance(envelope.data()));
    }
}
