package com.project.contracts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;

class EventContractsTest {

    // Tolerant reader: unknown properties are ignored
    private final JsonMapper mapper = JsonMapper.builder()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();

    private final UUID correlationId = UUID.randomUUID();

    private <T> void roundTrip(EventType type, T data, TypeReference<EventEnvelope<T>> ref) {
        EventEnvelope<T> original = EventEnvelope.of(type, correlationId, data);
        String json = mapper.writeValueAsString(original);
        EventEnvelope<T> copy = mapper.readValue(json, ref);
        assertThat(copy).isEqualTo(original);
    }

    @Test
    void memberRegisteredRoundTrip() {
        roundTrip(EventType.MEMBER_REGISTERED,
                new MemberRegistered(UUID.randomUUID(), "Asha", "asha@example.com", null),
                new TypeReference<EventEnvelope<MemberRegistered>>() {});
    }

    @Test
    void membershipPendingRoundTrip() {
        roundTrip(EventType.MEMBERSHIP_PENDING,
                new MembershipPending(UUID.randomUUID(), UUID.randomUUID(), 15000,
                        "asha@example.com",UUID.randomUUID() ),
                new TypeReference<EventEnvelope<MembershipPending>>() {});
    }

    @Test
    void invoiceCreatedRoundTrip() {
        roundTrip(EventType.INVOICE_GENERATED,
                new InvoiceCreated(UUID.randomUUID(), UUID.randomUUID(), "http://localhost/pay/1"),
                new TypeReference<EventEnvelope<InvoiceCreated>>() {});
    }

    @Test
    void paymentSucceededRoundTrip() {
        roundTrip(EventType.PAYMENT_COMPLETED,
                new PaymentSuccessfull(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                        150000L),
                new TypeReference<EventEnvelope<PaymentSuccessfull>>() {});
    }

    @Test
    void membershipActivatedRoundTrip() {
        roundTrip(EventType.MEMBERSHIP_ACTIVATED,
                new MembershipActivated(UUID.randomUUID(), UUID.randomUUID()),
                new TypeReference<EventEnvelope<MembershipActivated>>() {});
    }

    @Test
    void missingRequiredFieldFails() {
        // 'invoiceId' is missing from data
        String json = """
                {"eventId":"%s","type":"InvoiceCreated","version":1,
                 "occurredAt":"2026-11-12T10:00:00Z","correlationId":"%s",
                 "data":{"subscriptionId":"%s","paymentUrl":"http://x"}}
                """.formatted(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        assertThrows(JacksonException.class,
                () -> mapper.readValue(json, new TypeReference<EventEnvelope<InvoiceCreated>>() {}));
    }

    @Test
    void unknownExtraFieldIsTolerated() {
        UUID subscriptionId = UUID.randomUUID();
        UUID memberId = UUID.randomUUID();
        String json = """
                {"eventId":"%s","type":"MembershipActivated","version":1,
                 "occurredAt":"2026-11-12T10:00:00Z","correlationId":"%s","somethingNew":"x",
                 "data":{"subscriptionId":"%s","memberId":"%s","extra":123}}
                """.formatted(UUID.randomUUID(), UUID.randomUUID(), subscriptionId, memberId);

        EventEnvelope<MembershipActivated> envelope =
                mapper.readValue(json, new TypeReference<EventEnvelope<MembershipActivated>>() {});

        assertThat(envelope.data()).isEqualTo(new MembershipActivated(subscriptionId, memberId));
    }

    @Test
    void forPayloadMapsEveryPayloadClass() {
        for (EventType type : EventType.values()) {
            assertThat(EventType.forPayload(type.getPayloadClass())).isEqualTo(type);
        }
    }

    @Test
    void forPayloadFailsOnUnknownClass() {
        assertThrows(IllegalArgumentException.class, () -> EventType.forPayload(String.class));
    }
}