package com.example.volunteerhub.controller;

import com.example.volunteerhub.dto.UpcomingEventResponse;
import com.example.volunteerhub.entity.Event;
import com.example.volunteerhub.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // Create event
    @PostMapping
    public ResponseEntity<Event> createEvent(
            @Valid @RequestBody Event event) {

        Event savedEvent =
                eventService.createEvent(event);

        return ResponseEntity.ok(savedEvent);
    }

    // Get all events
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {

        return ResponseEntity.ok(
                eventService.getAllEvents());
    }

    // Get upcoming events
    @GetMapping("/upcoming")
    public ResponseEntity<List<UpcomingEventResponse>> getUpcomingEvents() {

        return ResponseEntity.ok(
                eventService.getUpcomingEvents());
    }

    // Get event by ID
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                eventService.getEventById(id));
    }

    // Update event
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody Event event) {

        return ResponseEntity.ok(
                eventService.updateEvent(id, event));
    }

    // Delete event
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable Long id) {

        eventService.deleteEvent(id);

        return ResponseEntity.noContent().build();
    }
}