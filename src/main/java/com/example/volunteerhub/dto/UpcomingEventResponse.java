package com.example.volunteerhub.dto;

import java.time.LocalDate;

public class UpcomingEventResponse {

    private Long id;
    private String name;
    private LocalDate date;
    private String location;
    private Integer capacity;
    private Long registered;
    private Long availableSlots;

    public UpcomingEventResponse() {
    }

    public UpcomingEventResponse(
            Long id,
            String name,
            LocalDate date,
            String location,
            Integer capacity,
            Long registered,
            Long availableSlots) {

        this.id = id;
        this.name = name;
        this.date = date;
        this.location = location;
        this.capacity = capacity;
        this.registered = registered;
        this.availableSlots = availableSlots;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Long getRegistered() {
        return registered;
    }

    public Long getAvailableSlots() {
        return availableSlots;
    }
}
