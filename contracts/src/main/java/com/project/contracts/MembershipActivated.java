package com.project.contracts;

import java.util.Objects;
import java.util.UUID;

public record MembershipActivated(UUID subscriptionId, UUID memberId) {
    public MembershipActivated {


        Objects.requireNonNull(subscriptionId,"subscriptionId");
        Objects.requireNonNull(memberId,"memberId");

    }
}
