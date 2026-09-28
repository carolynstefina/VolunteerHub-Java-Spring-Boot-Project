package com.example.volunteerhub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "attendance_records")
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "signup_id", nullable = false, unique = true)
    private Signup signup;

    private boolean attended;

    @NotNull(message = "Hours cannot be null")
    @PositiveOrZero(message = "Hours cannot be negative")
    private Integer hours;

    public AttendanceRecord() {
    }

    public AttendanceRecord(
            Signup signup,
            boolean attended,
            Integer hours) {

        this.signup = signup;
        this.attended = attended;
        this.hours = hours;
    }

    public Long getId() {
        return id;
    }

    public Signup getSignup() {
        return signup;
    }

    public void setSignup(Signup signup) {
        this.signup = signup;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }
}