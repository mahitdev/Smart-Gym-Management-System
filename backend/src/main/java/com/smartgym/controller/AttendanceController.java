package com.smartgym.controller;

import com.smartgym.dto.AttendanceRequest;
import com.smartgym.dto.MembershipStatusResponse;
import com.smartgym.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/mark")
    public MembershipStatusResponse mark(@Valid @RequestBody AttendanceRequest request) {
        return attendanceService.markAttendance(request);
    }
}
