package com.project.member;


import com.project.contracts.MemberRegistered;
import com.project.platform.EventPublisher;
import com.project.platform.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberRegistrationSevice {

    private final MemberRepository members;
    private final EventPublisher event;


    @Transactional
    public Member register(String name, String email){
        Member member = members.saveAndFlush(new Member(name,email.trim()));
        event.publish(new MemberRegistered(member.getId(), member.getName(), member.getEmail(),null));
        return member;
    }

    @Transactional(readOnly = true)
    public Member get(UUID id){
        return members.findById(id).orElseThrow(()-> new ResourceNotFoundException("Member "+id+ " not found"));
    }

}
