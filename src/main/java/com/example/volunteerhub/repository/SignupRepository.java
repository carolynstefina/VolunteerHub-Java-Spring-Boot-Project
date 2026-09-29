package com.example.volunteerhub.repository;

import com.example.volunteerhub.entity.Signup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignupRepository extends JpaRepository<Signup, Long> {

    long countByEventId(Long eventId);

}