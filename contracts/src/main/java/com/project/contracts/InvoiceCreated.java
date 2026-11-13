package com.project.contracts;

import java.util.Objects;
import java.util.UUID;

public record InvoiceCreated(UUID invoiceId,UUID subscriptionId, String paymentURL) {
    public InvoiceCreated{
        Objects.requireNonNull(invoiceId,"invoiceID");
        Objects.requireNonNull(subscriptionId,"subscriptionID");
        Objects.requireNonNull(paymentURL,"paymentURL");
    }
}
