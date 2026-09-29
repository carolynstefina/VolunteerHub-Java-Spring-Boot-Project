package com.example.volunteerhub.controller;

import com.example.volunteerhub.entity.Signup;
import com.example.volunteerhub.service.SignupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/signups")
public class SignupController {

    private final SignupService signupService;

    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }

    @PostMapping
    public ResponseEntity<Signup> createSignup(
            @RequestParam Long volunteerId,
            @RequestParam Long eventId) {

        Signup signup = signupService.createSignup(
                volunteerId,
                eventId
        );

        return ResponseEntity.ok(signup);
    }

    @GetMapping
    public ResponseEntity<List<Signup>> getAllSignups() {

        return ResponseEntity.ok(
                signupService.getAllSignups()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Signup> getSignupById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                signupService.getSignupById(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSignup(
            @PathVariable Long id) {

        signupService.deleteSignup(id);

        return ResponseEntity.noContent().build();
    }
}