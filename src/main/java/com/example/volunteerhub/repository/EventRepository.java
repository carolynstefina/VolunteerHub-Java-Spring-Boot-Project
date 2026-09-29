package com.example.volunteerhub.repository;

import com.example.volunteerhub.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByDateGreaterThanEqualOrderByDateAsc(LocalDate date);
}