package com.smartgym.service;

import com.smartgym.dto.DashboardResponse;
import com.smartgym.model.MemberStatus;
import com.smartgym.repository.MemberRepository;
import com.smartgym.repository.MembershipRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;
    private final PaymentService paymentService;

    public DashboardService(MemberRepository memberRepository,
                            MembershipRepository membershipRepository,
                            PaymentService paymentService) {
        this.memberRepository = memberRepository;
        this.membershipRepository = membershipRepository;
        this.paymentService = paymentService;
    }

    public DashboardResponse getSummary() {
        DashboardResponse response = new DashboardResponse();
        response.setTotalMembers(memberRepository.count());
        response.setActiveMembers(memberRepository.countByStatus(MemberStatus.ACTIVE));
        response.setInactiveMembers(memberRepository.countByStatus(MemberStatus.INACTIVE) +
                memberRepository.countByStatus(MemberStatus.EXPIRED));
        response.setMembersCompletedAtLeastOneMonth(membershipRepository.countByCompletedMonthsGreaterThan(0));
        response.setMonthlyRevenue(paymentService.getRevenueForCurrentMonth());
        return response;
    }
}
