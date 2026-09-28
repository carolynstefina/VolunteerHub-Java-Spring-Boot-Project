package com.example.volunteerhub.service;

import com.example.volunteerhub.entity.AttendanceRecord;
import com.example.volunteerhub.entity.Signup;
import com.example.volunteerhub.exception.InvalidAttendanceException;
import com.example.volunteerhub.exception.ResourceNotFoundException;
import com.example.volunteerhub.repository.AttendanceRecordRepository;
import com.example.volunteerhub.repository.SignupRepository;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {

    private final AttendanceRecordRepository attendanceRecordRepository;
    private final SignupRepository signupRepository;

    public AttendanceService(
            AttendanceRecordRepository attendanceRecordRepository,
            SignupRepository signupRepository) {

        this.attendanceRecordRepository = attendanceRecordRepository;
        this.signupRepository = signupRepository;
    }

    public AttendanceRecord markAttendance(
            Long signupId,
            boolean attended,
            Integer hours) {

        Signup signup = signupRepository.findById(signupId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Signup not found with id: " + signupId));

        if (hours == null) {
            throw new InvalidAttendanceException(
                    "Hours cannot be null.");
        }

        if (hours < 0) {
            throw new InvalidAttendanceException(
                    "Hours cannot be negative.");
        }

        if (!attended && hours > 0) {
            throw new InvalidAttendanceException(
                    "Hours can only be recorded when volunteer is marked as attended.");
        }

        AttendanceRecord attendanceRecord =
                attendanceRecordRepository
                        .findBySignupId(signupId)
                        .orElseGet(AttendanceRecord::new);

        attendanceRecord.setSignup(signup);
        attendanceRecord.setAttended(attended);
        attendanceRecord.setHours(hours);

        return attendanceRecordRepository.save(attendanceRecord);
    }

    public AttendanceRecord getAttendanceById(Long id) {

        return attendanceRecordRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Attendance record not found with id: " + id));
    }
}