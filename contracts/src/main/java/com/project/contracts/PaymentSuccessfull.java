package com.project.contracts;

import java.util.Objects;
import java.util.UUID;

public record PaymentSuccessfull(UUID paymentID, UUID invoiceID, UUID memberID, long amount) {
    public PaymentSuccessfull{
        Objects.requireNonNull(paymentID,"paymentId");
        Objects.requireNonNull(invoiceID,"invoiceId");
        Objects.requireNonNull(memberID,"memberId");
    }

}
