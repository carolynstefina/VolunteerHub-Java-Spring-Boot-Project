package com.example.volunteerhub.service;

import com.example.volunteerhub.dto.UpcomingEventResponse;
import com.example.volunteerhub.entity.Event;
import com.example.volunteerhub.exception.ResourceNotFoundException;
import com.example.volunteerhub.repository.EventRepository;
import com.example.volunteerhub.repository.SignupRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final SignupRepository signupRepository;

    public EventService(
            EventRepository eventRepository,
            SignupRepository signupRepository) {

        this.eventRepository = eventRepository;
        this.signupRepository = signupRepository;
    }

    // Create event
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    // Get all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Get event by ID
    public Event getEventById(Long id) {

        return eventRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Event not found with id: " + id));
    }

    // Update event
    public Event updateEvent(
            Long id,
            Event updatedEvent) {

        Event existingEvent = getEventById(id);

        existingEvent.setName(updatedEvent.getName());
        existingEvent.setDate(updatedEvent.getDate());
        existingEvent.setLocation(updatedEvent.getLocation());
        existingEvent.setCapacity(updatedEvent.getCapacity());

        return eventRepository.save(existingEvent);
    }

    // Delete event
    public void deleteEvent(Long id) {

        Event existingEvent = getEventById(id);

        eventRepository.delete(existingEvent);
    }

    // Get upcoming events with available slots
    public List<UpcomingEventResponse> getUpcomingEvents() {

        List<Event> upcomingEvents =
                eventRepository
                        .findByDateGreaterThanEqualOrderByDateAsc(
                                LocalDate.now());

        return upcomingEvents.stream()
                .map(event -> {

                    long registered =
                            signupRepository.countByEventId(event.getId());

                    long availableSlots =
                            event.getCapacity() - registered;

                    return new UpcomingEventResponse(
                            event.getId(),
                            event.getName(),
                            event.getDate(),
                            event.getLocation(),
                            event.getCapacity(),
                            registered,
                            availableSlots
                    );
                })
                .toList();
    }
}