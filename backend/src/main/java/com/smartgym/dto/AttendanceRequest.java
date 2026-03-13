package com.smartgym.dto;

import com.smartgym.model.AttendanceSource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class AttendanceRequest {
    @NotNull
    private Long memberId;
    private LocalDate attendanceDate;
    @NotNull
    private AttendanceSource source;
    @NotBlank
    private String markedBy;

    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }
    public LocalDate getAttendanceDate() { return attendanceDate; }
    public void setAttendanceDate(LocalDate attendanceDate) { this.attendanceDate = attendanceDate; }
    public AttendanceSource getSource() { return source; }
    public void setSource(AttendanceSource source) { this.source = source; }
    public String getMarkedBy() { return markedBy; }
    public void setMarkedBy(String markedBy) { this.markedBy = markedBy; }
}
