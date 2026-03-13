package com.smartgym.repository;

import com.smartgym.model.Member;
import com.smartgym.model.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    long countByStatus(MemberStatus status);
}
