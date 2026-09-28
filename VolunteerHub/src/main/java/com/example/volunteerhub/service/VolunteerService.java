package com.example.volunteerhub.service;

import com.example.volunteerhub.entity.Volunteer;
import com.example.volunteerhub.exception.ResourceNotFoundException;
import com.example.volunteerhub.repository.AttendanceRecordRepository;
import com.example.volunteerhub.repository.VolunteerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final AttendanceRecordRepository attendanceRecordRepository;

    public VolunteerService(
            VolunteerRepository volunteerRepository,
            AttendanceRecordRepository attendanceRecordRepository) {

        this.volunteerRepository = volunteerRepository;
        this.attendanceRecordRepository = attendanceRecordRepository;
    }

    // Create a new volunteer
    public Volunteer createVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    // Get all volunteers
    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    // Get volunteer by ID
    public Volunteer getVolunteerById(Long id) {

        return volunteerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Volunteer not found with id: " + id));
    }

    // Update volunteer
    public Volunteer updateVolunteer(
            Long id,
            Volunteer updatedVolunteer) {

        Volunteer existingVolunteer =
                getVolunteerById(id);

        existingVolunteer.setName(
                updatedVolunteer.getName());

        existingVolunteer.setEmail(
                updatedVolunteer.getEmail());

        existingVolunteer.setPhone(
                updatedVolunteer.getPhone());

        return volunteerRepository.save(existingVolunteer);
    }

    // Delete volunteer
    public void deleteVolunteer(Long id) {

        Volunteer existingVolunteer =
                getVolunteerById(id);

        volunteerRepository.delete(existingVolunteer);
    }

    // Get total contributed hours of a volunteer
    public Long getTotalHoursByVolunteerId(Long volunteerId) {

        // First check whether volunteer exists
        getVolunteerById(volunteerId);

        return attendanceRecordRepository
                .getTotalHoursByVolunteerId(volunteerId);
    }
}