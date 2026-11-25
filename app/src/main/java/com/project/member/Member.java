package com.project.member;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "members", schema = "member_db")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;


    private String cognitoSub;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Instant createdAt;


    public Member(String name, String email){
       this.id = UUID.randomUUID();
       this.name = name;
       this.email = email;
       this.status = "ACTIVE";
       this.createdAt  = Instant.now();
    }

}
