package com.smartgym.repository;

import com.smartgym.model.Attendance;
import com.smartgym.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByMemberAndAttendanceDate(Member member, LocalDate attendanceDate);
}
