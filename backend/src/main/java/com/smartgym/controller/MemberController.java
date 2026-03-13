package com.smartgym.controller;

import com.smartgym.dto.MemberRegistrationRequest;
import com.smartgym.dto.MemberResponse;
import com.smartgym.dto.MembershipStatusResponse;
import com.smartgym.service.AttendanceService;
import com.smartgym.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final AttendanceService attendanceService;

    public MemberController(MemberService memberService, AttendanceService attendanceService) {
        this.memberService = memberService;
        this.attendanceService = attendanceService;
    }

    @PostMapping("/register")
    public MemberResponse register(@Valid @RequestBody MemberRegistrationRequest request) {
        return memberService.register(request);
    }

    @GetMapping("/{memberId}/membership-status")
    public MembershipStatusResponse membershipStatus(@PathVariable Long memberId) {
        return attendanceService.getStatusByMemberId(memberId);
    }
}
