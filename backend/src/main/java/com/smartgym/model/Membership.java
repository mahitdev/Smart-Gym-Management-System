package com.smartgym.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "memberships")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MembershipType membershipType;

    @Column(nullable = false)
    private int purchasedMonths;

    @Column(nullable = false)
    private int completedMonths;

    @Column(nullable = false)
    private int attendanceDaysInCurrentMonth;

    @Column(nullable = false)
    private int totalAttendanceDays;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private LocalDate startedOn;

    private LocalDate endedOn;

    @PrePersist
    public void prePersist() {
        if (startedOn == null) {
            startedOn = LocalDate.now();
        }
    }

    public Long getId() { return id; }
    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
    public MembershipType getMembershipType() { return membershipType; }
    public void setMembershipType(MembershipType membershipType) { this.membershipType = membershipType; }
    public int getPurchasedMonths() { return purchasedMonths; }
    public void setPurchasedMonths(int purchasedMonths) { this.purchasedMonths = purchasedMonths; }
    public int getCompletedMonths() { return completedMonths; }
    public void setCompletedMonths(int completedMonths) { this.completedMonths = completedMonths; }
    public int getAttendanceDaysInCurrentMonth() { return attendanceDaysInCurrentMonth; }
    public void setAttendanceDaysInCurrentMonth(int attendanceDaysInCurrentMonth) { this.attendanceDaysInCurrentMonth = attendanceDaysInCurrentMonth; }
    public int getTotalAttendanceDays() { return totalAttendanceDays; }
    public void setTotalAttendanceDays(int totalAttendanceDays) { this.totalAttendanceDays = totalAttendanceDays; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public LocalDate getStartedOn() { return startedOn; }
    public void setStartedOn(LocalDate startedOn) { this.startedOn = startedOn; }
    public LocalDate getEndedOn() { return endedOn; }
    public void setEndedOn(LocalDate endedOn) { this.endedOn = endedOn; }
}
