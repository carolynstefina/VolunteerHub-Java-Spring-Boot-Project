package com.example.volunteerhub.controller;

import com.example.volunteerhub.entity.AttendanceRecord;
import com.example.volunteerhub.service.AttendanceService;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
@Validated
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PutMapping("/{signupId}")
    public ResponseEntity<AttendanceRecord> markAttendance(
            @PathVariable Long signupId,
            @RequestParam boolean attended,
            @RequestParam
            @PositiveOrZero(message = "Hours cannot be negative")
            Integer hours) {

        AttendanceRecord attendanceRecord =
                attendanceService.markAttendance(
                        signupId,
                        attended,
                        hours);

        return ResponseEntity.ok(attendanceRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceRecord> getAttendance(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceById(id));
    }
}