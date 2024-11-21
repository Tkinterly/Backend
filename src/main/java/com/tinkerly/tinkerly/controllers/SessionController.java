package com.tinkerly.tinkerly.controllers;

import com.tinkerly.tinkerly.components.ProfileGenerator;
import com.tinkerly.tinkerly.entities.Sessions;
import com.tinkerly.tinkerly.payloads.WorkerProfile;
import com.tinkerly.tinkerly.repositories.SessionsRepository;
import com.tinkerly.tinkerly.services.Tokenization;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin
public class SessionController {
    protected final SessionsRepository sessionsRepository;
    protected final ProfileGenerator profileGenerator;

    public SessionController(
            SessionsRepository sessionsRepository,
            ProfileGenerator profileGenerator
    ) {
        this.sessionsRepository = sessionsRepository;
        this.profileGenerator = profileGenerator;
    }

    protected boolean isValidSession() {
        String token = Tokenization.getToken();
        Optional<Sessions> sessionQuery = this.sessionsRepository.findByToken(token);

        if (sessionQuery.isEmpty()) {
            return false;
        }

        Sessions session = sessionQuery.get();
        String userId = session.getUserId();
        Optional<WorkerProfile> workerProfile = this.profileGenerator.getWorkerOnlyProfile(userId);
        Instant dateRightNow = new Date().toInstant();

        return sessionQuery.get().getExpiry().toInstant().isAfter(dateRightNow)
                && (workerProfile.isEmpty()
                        || !workerProfile.get().isBanned()
                        || (workerProfile.get().getSuspension() != null
                                && workerProfile.get().getSuspension().toInstant().isAfter(dateRightNow))
        );
    }

    protected Optional<Sessions> getSession() {
        return this.sessionsRepository.findByToken(Tokenization.getToken());
    }
}
