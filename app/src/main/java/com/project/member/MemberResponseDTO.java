package com.project.member;

import java.time.Instant;
import java.util.UUID;

public record MemberResponseDTO(UUID id, String name, String email, String status, Instant createdAt) {
   static MemberResponseDTO from(Member member){
        return new MemberResponseDTO(member.getId(), member.getName(), member.getEmail(), member.getStatus(), member.getCreatedAt());
    }
}
