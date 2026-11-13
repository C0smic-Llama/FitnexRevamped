package com.project.contracts;

import java.util.Objects;
import java.util.UUID;

public record MemberRegistered (UUID memberId, String name, String email, String cognitoSub){
    public MemberRegistered{
        Objects.requireNonNull(memberId,"memberId");
        Objects.requireNonNull(name,"name");
        Objects.requireNonNull(email,"email");
        //cognitosub is nullable ig, in v0
    }
}
