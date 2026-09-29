package com.example.volunteerhub.controller;

import com.example.volunteerhub.entity.Volunteer;
import com.example.volunteerhub.service.VolunteerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/volunteers")
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    // Create volunteer
    @PostMapping
    public ResponseEntity<Volunteer> createVolunteer(
            @Valid @RequestBody Volunteer volunteer) {

        Volunteer savedVolunteer =
                volunteerService.createVolunteer(volunteer);

        return ResponseEntity.ok(savedVolunteer);
    }

    // Get all volunteers
    @GetMapping
    public ResponseEntity<List<Volunteer>> getAllVolunteers() {

        return ResponseEntity.ok(
                volunteerService.getAllVolunteers());
    }

    // Get volunteer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> getVolunteerById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                volunteerService.getVolunteerById(id));
    }

    // Update volunteer
    @PutMapping("/{id}")
    public ResponseEntity<Volunteer> updateVolunteer(
            @PathVariable Long id,
            @Valid @RequestBody Volunteer volunteer) {

        return ResponseEntity.ok(
                volunteerService.updateVolunteer(id, volunteer));
    }

    // Delete volunteer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolunteer(
            @PathVariable Long id) {

        volunteerService.deleteVolunteer(id);

        return ResponseEntity.noContent().build();
    }

    // Get total contributed hours of a volunteer
    @GetMapping("/{id}/hours")
    public ResponseEntity<Long> getTotalHours(
            @PathVariable Long id) {

        Long totalHours =
                volunteerService.getTotalHoursByVolunteerId(id);

        return ResponseEntity.ok(totalHours);
    }
}