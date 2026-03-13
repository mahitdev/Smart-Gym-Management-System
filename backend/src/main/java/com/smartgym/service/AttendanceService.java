package com.smartgym.service;

import com.smartgym.dto.AttendanceRequest;
import com.smartgym.dto.MembershipStatusResponse;
import com.smartgym.exception.BusinessException;
import com.smartgym.exception.ResourceNotFoundException;
import com.smartgym.model.*;
import com.smartgym.repository.AttendanceRepository;
import com.smartgym.repository.MemberRepository;
import com.smartgym.repository.MembershipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class AttendanceService {

    private static final int ATTENDANCE_DAYS_PER_MONTH = 26;

    private final AttendanceRepository attendanceRepository;
    private final MembershipRepository membershipRepository;
    private final MemberRepository memberRepository;

    public AttendanceService(AttendanceRepository attendanceRepository,
                             MembershipRepository membershipRepository,
                             MemberRepository memberRepository) {
        this.attendanceRepository = attendanceRepository;
        this.membershipRepository = membershipRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public MembershipStatusResponse markAttendance(AttendanceRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found: " + request.getMemberId()));

        Membership membership = membershipRepository.findFirstByMemberOrderByIdDesc(member)
                .orElseThrow(() -> new BusinessException("No membership found. Advance payment is required before access."));

        if (!membership.isActive()) {
            throw new BusinessException("Membership is inactive. Advance payment is required before access.");
        }

        LocalDate attendanceDate = request.getAttendanceDate() == null ? LocalDate.now() : request.getAttendanceDate();
        attendanceRepository.findByMemberAndAttendanceDate(member, attendanceDate).ifPresent(existing -> {
            throw new BusinessException("Attendance already marked for " + attendanceDate);
        });

        Attendance attendance = new Attendance();
        attendance.setMember(member);
        attendance.setAttendanceDate(attendanceDate);
        attendance.setSource(request.getSource());
        attendance.setMarkedBy(request.getMarkedBy());
        attendanceRepository.save(attendance);

        int currentCount = membership.getAttendanceDaysInCurrentMonth() + 1;
        membership.setTotalAttendanceDays(membership.getTotalAttendanceDays() + 1);

        if (currentCount == ATTENDANCE_DAYS_PER_MONTH) {
            membership.setCompletedMonths(membership.getCompletedMonths() + 1);
            membership.setAttendanceDaysInCurrentMonth(0);
        } else {
            membership.setAttendanceDaysInCurrentMonth(currentCount);
        }

        if (membership.getCompletedMonths() >= membership.getPurchasedMonths()) {
            membership.setActive(false);
            membership.setEndedOn(LocalDate.now());
            member.setStatus(MemberStatus.EXPIRED);
        } else {
            member.setStatus(MemberStatus.ACTIVE);
        }

        membershipRepository.save(membership);
        memberRepository.save(member);

        return toResponse(membership);
    }

    public MembershipStatusResponse getStatusByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found: " + memberId));
        Membership membership = membershipRepository.findFirstByMemberOrderByIdDesc(member)
                .orElseThrow(() -> new ResourceNotFoundException("Membership not found for member: " + memberId));
        return toResponse(membership);
    }

    public MembershipStatusResponse toResponse(Membership membership) {
        MembershipStatusResponse response = new MembershipStatusResponse();
        response.setMembershipId(membership.getId());
        response.setMembershipType(membership.getMembershipType());
        response.setPurchasedMonths(membership.getPurchasedMonths());
        response.setCompletedMonths(membership.getCompletedMonths());
        response.setAttendanceDaysInCurrentMonth(membership.getAttendanceDaysInCurrentMonth());
        response.setTotalAttendanceDays(membership.getTotalAttendanceDays());
        response.setActive(membership.isActive());
        response.setStartedOn(membership.getStartedOn());
        response.setEndedOn(membership.getEndedOn());
        return response;
    }
}
