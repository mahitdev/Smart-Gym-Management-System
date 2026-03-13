package com.smartgym;

import com.smartgym.dto.AttendanceRequest;
import com.smartgym.dto.MembershipStatusResponse;
import com.smartgym.model.*;
import com.smartgym.repository.AttendanceRepository;
import com.smartgym.repository.MemberRepository;
import com.smartgym.repository.MembershipRepository;
import com.smartgym.service.AttendanceService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AttendanceServiceTest {

    @Test
    void shouldCompleteMonthAt26thAttendance() {
        AttendanceRepository attendanceRepository = mock(AttendanceRepository.class);
        MembershipRepository membershipRepository = mock(MembershipRepository.class);
        MemberRepository memberRepository = mock(MemberRepository.class);

        AttendanceService service = new AttendanceService(attendanceRepository, membershipRepository, memberRepository);

        Member member = new Member();
        member.setStatus(MemberStatus.ACTIVE);

        Membership membership = new Membership();
        membership.setMember(member);
        membership.setActive(true);
        membership.setPurchasedMonths(3);
        membership.setCompletedMonths(0);
        membership.setAttendanceDaysInCurrentMonth(25);
        membership.setTotalAttendanceDays(25);
        membership.setMembershipType(MembershipType.QUARTERLY);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(membershipRepository.findFirstByMemberOrderByIdDesc(member)).thenReturn(Optional.of(membership));
        when(attendanceRepository.findByMemberAndAttendanceDate(eq(member), any(LocalDate.class))).thenReturn(Optional.empty());

        AttendanceRequest request = new AttendanceRequest();
        request.setMemberId(1L);
        request.setSource(AttendanceSource.QR);
        request.setMarkedBy("gate-1");
        request.setAttendanceDate(LocalDate.now());

        MembershipStatusResponse response = service.markAttendance(request);

        assertEquals(1, response.getCompletedMonths());
        assertEquals(0, response.getAttendanceDaysInCurrentMonth());
        assertEquals(26, response.getTotalAttendanceDays());

        ArgumentCaptor<Membership> captor = ArgumentCaptor.forClass(Membership.class);
        verify(membershipRepository).save(captor.capture());
        assertEquals(1, captor.getValue().getCompletedMonths());
    }
}
