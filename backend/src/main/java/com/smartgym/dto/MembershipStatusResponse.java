package com.smartgym.dto;

import com.smartgym.model.MembershipType;

import java.time.LocalDate;

public class MembershipStatusResponse {
    private Long membershipId;
    private MembershipType membershipType;
    private int purchasedMonths;
    private int completedMonths;
    private int attendanceDaysInCurrentMonth;
    private int totalAttendanceDays;
    private boolean active;
    private LocalDate startedOn;
    private LocalDate endedOn;

    public Long getMembershipId() { return membershipId; }
    public void setMembershipId(Long membershipId) { this.membershipId = membershipId; }
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
