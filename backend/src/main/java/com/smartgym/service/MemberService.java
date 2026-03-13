package com.smartgym.service;

import com.smartgym.dto.MemberRegistrationRequest;
import com.smartgym.dto.MemberResponse;
import com.smartgym.exception.ResourceNotFoundException;
import com.smartgym.model.Member;
import com.smartgym.model.MemberStatus;
import com.smartgym.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse register(MemberRegistrationRequest request) {
        Member member = new Member();
        member.setFullName(request.getFullName());
        member.setPhone(request.getPhone());
        member.setEmail(request.getEmail());
        member.setStatus(MemberStatus.INACTIVE);

        Member saved = memberRepository.save(member);
        return toResponse(saved);
    }

    public Member getById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found: " + id));
    }

    public MemberResponse toResponse(Member member) {
        MemberResponse response = new MemberResponse();
        response.setId(member.getId());
        response.setMemberCode(member.getMemberCode());
        response.setFullName(member.getFullName());
        response.setPhone(member.getPhone());
        response.setEmail(member.getEmail());
        response.setQrCode(member.getQrCode());
        response.setStatus(member.getStatus());
        return response;
    }
}
