package com.project.contracts;

import java.util.Objects;
import java.util.UUID;

public record MembershipPending (UUID subscriptionID, UUID memberID, long amount, String email, UUID planID){
    public MembershipPending{
        Objects.requireNonNull(subscriptionID,"subscriptionID");
        Objects.requireNonNull(memberID,"memberID");
        Objects.requireNonNull(email,"email");
        Objects.requireNonNull(planID,"planId");
    }
}
