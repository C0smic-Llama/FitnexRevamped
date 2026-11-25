package com.project.member;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberRegistrationSevice service;

    @PostMapping
    ResponseEntity<MemberResponseDTO> register(@Valid
                                               @RequestBody
                                               MemberRequestDTO req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(MemberResponseDTO.from(service.register(req.name(), req.email())));
    }

    @GetMapping
    MemberResponseDTO get(@PathVariable
                          UUID id){
        return MemberResponseDTO.from(service.get(id));
    }
}
