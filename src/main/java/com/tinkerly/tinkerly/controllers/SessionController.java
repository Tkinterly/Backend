package com.tinkerly.tinkerly.controllers;

import com.tinkerly.tinkerly.entities.Sessions;
import com.tinkerly.tinkerly.repositories.SessionsRepository;
import com.tinkerly.tinkerly.services.Tokenization;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin
public class SessionController {
    protected final SessionsRepository sessionsRepository;

    public SessionController(SessionsRepository sessionsRepository) {
        this.sessionsRepository = sessionsRepository;
    }

    protected boolean isValidSession() {
        System.out.println(Tokenization.getToken());
        Optional<Sessions> sessionQuery = this.sessionsRepository.findByToken(Tokenization.getToken());

        return sessionQuery.isPresent() && sessionQuery.get().getExpiry().toInstant().isAfter(new Date().toInstant());
    }

    protected Optional<Sessions> getSession() {
        return this.sessionsRepository.findByToken(Tokenization.getToken());
    }
}
