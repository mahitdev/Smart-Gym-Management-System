package com.smartgym.service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.smartgym.dto.PaymentRequest;
import com.smartgym.exception.ResourceNotFoundException;
import com.smartgym.model.*;
import com.smartgym.repository.MemberRepository;
import com.smartgym.repository.MembershipRepository;
import com.smartgym.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          MemberRepository memberRepository,
                          MembershipRepository membershipRepository) {
        this.paymentRepository = paymentRepository;
        this.memberRepository = memberRepository;
        this.membershipRepository = membershipRepository;
    }

    @Transactional
    public Payment makeAdvancePayment(PaymentRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found: " + request.getMemberId()));

        Payment payment = new Payment();
        payment.setMember(member);
        payment.setMembershipType(request.getMembershipType());
        payment.setAmount(request.getAmount());
        payment.setStatus(PaymentStatus.PAID);
        payment.setPaidAt(LocalDateTime.now());
        payment.setInvoiceNumber("INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        Payment savedPayment = paymentRepository.save(payment);

        Membership membership = membershipRepository.findFirstByMemberOrderByIdDesc(member).orElseGet(Membership::new);
        if (membership.getId() == null) {
            membership.setMember(member);
            membership.setCompletedMonths(0);
            membership.setTotalAttendanceDays(0);
            membership.setAttendanceDaysInCurrentMonth(0);
            membership.setStartedOn(LocalDate.now());
            membership.setPurchasedMonths(request.getMembershipType().getMonths());
        } else {
            membership.setPurchasedMonths(membership.getPurchasedMonths() + request.getMembershipType().getMonths());
        }
        membership.setMembershipType(request.getMembershipType());
        membership.setActive(true);
        membership.setEndedOn(null);
        membershipRepository.save(membership);

        member.setStatus(MemberStatus.ACTIVE);
        memberRepository.save(member);

        return savedPayment;
    }

    public byte[] generateInvoicePdf(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found: " + paymentId));

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.add(new Paragraph("Smart Gym Invoice"));
            document.add(new Paragraph("Invoice Number: " + payment.getInvoiceNumber()));
            document.add(new Paragraph("Member: " + payment.getMember().getFullName()));
            document.add(new Paragraph("Membership: " + payment.getMembershipType()));
            document.add(new Paragraph("Amount: " + payment.getAmount()));
            document.add(new Paragraph("Paid At: " + payment.getPaidAt()));
            document.close();
            return outputStream.toByteArray();
        } catch (DocumentException | java.io.IOException ex) {
            throw new RuntimeException("Failed to generate invoice PDF", ex);
        }
    }

    public BigDecimal getRevenueForCurrentMonth() {
        YearMonth month = YearMonth.now();
        LocalDateTime start = month.atDay(1).atStartOfDay();
        LocalDateTime end = month.atEndOfMonth().atTime(23, 59, 59);
        return paymentRepository.sumRevenueByDateRange(PaymentStatus.PAID, start, end);
    }
}
