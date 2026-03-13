package com.smartgym.model;

public enum MembershipType {
    MONTHLY(1),
    QUARTERLY(3),
    YEARLY(12);

    private final int months;

    MembershipType(int months) {
        this.months = months;
    }

    public int getMonths() {
        return months;
    }
}
