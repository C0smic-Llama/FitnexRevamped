package com.project.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberRequestDTO(
    @NotBlank
    @Size(max = 100)
    String name,
    @NotBlank
    @Email
    @Size(max = 250)
    String email){}
