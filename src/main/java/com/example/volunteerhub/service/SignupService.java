package com.example.volunteerhub.service;

import com.example.volunteerhub.entity.Event;
import com.example.volunteerhub.entity.Signup;
import com.example.volunteerhub.entity.Volunteer;
import com.example.volunteerhub.exception.EventFullException;
import com.example.volunteerhub.exception.ResourceNotFoundException;
import com.example.volunteerhub.repository.EventRepository;
import com.example.volunteerhub.repository.SignupRepository;
import com.example.volunteerhub.repository.VolunteerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SignupService {

    private final SignupRepository signupRepository;
    private final VolunteerRepository volunteerRepository;
    private final EventRepository eventRepository;

    public SignupService(
            SignupRepository signupRepository,
            VolunteerRepository volunteerRepository,
            EventRepository eventRepository) {

        this.signupRepository = signupRepository;
        this.volunteerRepository = volunteerRepository;
        this.eventRepository = eventRepository;
    }

    public Signup createSignup(Long volunteerId, Long eventId) {

        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Volunteer not found with id: " + volunteerId));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Event not found with id: " + eventId));

        long currentSignups = signupRepository.countByEventId(eventId);

        if (currentSignups >= event.getCapacity()) {
            throw new EventFullException(
                    "Event is full. No more signups are allowed.");
        }

        Signup signup = new Signup();

        signup.setVolunteer(volunteer);
        signup.setEvent(event);
        signup.setSignupDate(LocalDate.now());

        return signupRepository.save(signup);
    }

    public List<Signup> getAllSignups() {

        return signupRepository.findAll();
    }

    public Signup getSignupById(Long id) {

        return signupRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Signup not found with id: " + id));
    }

    public void deleteSignup(Long id) {

        Signup signup = getSignupById(id);

        signupRepository.delete(signup);
    }
}