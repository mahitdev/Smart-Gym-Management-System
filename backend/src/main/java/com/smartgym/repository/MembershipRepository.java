package com.smartgym.repository;

import com.smartgym.model.Member;
import com.smartgym.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    Optional<Membership> findFirstByMemberOrderByIdDesc(Member member);
    long countByCompletedMonthsGreaterThan(int value);
}
