package com.smartgym.repository;

import com.smartgym.model.Payment;
import com.smartgym.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("select coalesce(sum(p.amount), 0) from Payment p where p.status = :status and p.paidAt between :start and :end")
    BigDecimal sumRevenueByDateRange(PaymentStatus status, LocalDateTime start, LocalDateTime end);
}
