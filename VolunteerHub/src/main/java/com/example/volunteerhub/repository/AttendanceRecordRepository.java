package com.example.volunteerhub.repository;

import com.example.volunteerhub.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AttendanceRecordRepository
        extends JpaRepository<AttendanceRecord, Long> {

    Optional<AttendanceRecord> findBySignupId(Long signupId);

    @Query("""
            SELECT COALESCE(SUM(a.hours), 0)
            FROM AttendanceRecord a
            WHERE a.signup.volunteer.id = :volunteerId
              AND a.attended = true
            """)
    Long getTotalHoursByVolunteerId(
            @Param("volunteerId") Long volunteerId);
}