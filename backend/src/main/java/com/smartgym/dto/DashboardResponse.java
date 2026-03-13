package com.smartgym.dto;

import java.math.BigDecimal;

public class DashboardResponse {
    private long totalMembers;
    private long activeMembers;
    private long inactiveMembers;
    private long membersCompletedAtLeastOneMonth;
    private BigDecimal monthlyRevenue;

    public long getTotalMembers() { return totalMembers; }
    public void setTotalMembers(long totalMembers) { this.totalMembers = totalMembers; }
    public long getActiveMembers() { return activeMembers; }
    public void setActiveMembers(long activeMembers) { this.activeMembers = activeMembers; }
    public long getInactiveMembers() { return inactiveMembers; }
    public void setInactiveMembers(long inactiveMembers) { this.inactiveMembers = inactiveMembers; }
    public long getMembersCompletedAtLeastOneMonth() { return membersCompletedAtLeastOneMonth; }
    public void setMembersCompletedAtLeastOneMonth(long membersCompletedAtLeastOneMonth) { this.membersCompletedAtLeastOneMonth = membersCompletedAtLeastOneMonth; }
    public BigDecimal getMonthlyRevenue() { return monthlyRevenue; }
    public void setMonthlyRevenue(BigDecimal monthlyRevenue) { this.monthlyRevenue = monthlyRevenue; }
}
