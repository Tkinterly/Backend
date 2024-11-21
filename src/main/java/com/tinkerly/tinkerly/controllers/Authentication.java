package com.tinkerly.tinkerly.controllers;

import com.tinkerly.tinkerly.components.EndpointResponse;
import com.tinkerly.tinkerly.components.ProfileGenerator;
import com.tinkerly.tinkerly.entities.*;
import com.tinkerly.tinkerly.payloads.*;
import com.tinkerly.tinkerly.repositories.*;
import com.tinkerly.tinkerly.services.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

@RestController
@CrossOrigin
public class Authentication extends SessionController {
    private final ProfileGenerator profileGenerator;
    private final CredentialsRepository credentialsRepository;
    private final WorkDetailsRepository workDetailsRepository;

    private PasswordEncoder passwordEncoder;

    public Authentication(
            ProfileGenerator profileGenerator,
            SessionsRepository sessionsRepository,
            CredentialsRepository credentialsRepository,
            WorkDetailsRepository workDetailsRepository
    ) {
        super(sessionsRepository, profileGenerator);
        this.profileGenerator = profileGenerator;
        this.credentialsRepository = credentialsRepository;
        this.workDetailsRepository = workDetailsRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private LoginResponse populateLoginData(String userId, String token) {
        Optional<Profile> userProfile = this.profileGenerator.getGenericProfile(userId);

        if (userProfile.isEmpty()) {
            return null;
        }

        List<WorkDetails> workDetailsList = new ArrayList<>();
        Iterable<WorkDetails> workDetailsIterable = this.workDetailsRepository.findAll();
        for (WorkDetails workDetails : workDetailsIterable) {
            workDetailsList.add(workDetails);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 90);

        Sessions session = new Sessions(
                userProfile.get().getUserId(),
                token == null ? UUID.randomUUID().toString() : token,
                calendar.getTime()
        );

        if (token == null) {
            this.sessionsRepository.save(session);
        }

        return new LoginResponse(
                userProfile.get(),
                workDetailsList,
                session.getToken()
        );
    }

    @PostMapping("/authenticate/token")
    public EndpointResponse<LoginResponse> authenticateToken(@RequestBody TokenLogin tokenLogin) {
        Optional<Sessions> sessionQuery = this.sessionsRepository.findByToken(tokenLogin.getToken());
        if (sessionQuery.isEmpty()) {
            return EndpointResponse.failed("Invalid session!");
        }


        Sessions session = sessionQuery.get();
        String userId = session.getUserId();
        Optional<WorkerProfile> workerProfile = this.profileGenerator.getWorkerOnlyProfile(userId);
        Instant dateRightNow = new Date().toInstant();

        if (workerProfile.isPresent() && (
                workerProfile.get().isBanned()
                || (workerProfile.get().getSuspension() != null
                        && workerProfile.get().getSuspension().toInstant().isAfter(dateRightNow))
        )) {
            return EndpointResponse.failed("Worker cannot access platform!");
        }

        return EndpointResponse.passed(this.populateLoginData(sessionQuery.get().getUserId(), tokenLogin.getToken()));
    }

    @PostMapping("/authenticate/credentials")
    public EndpointResponse<LoginResponse> authenticateCredentials(@RequestBody CredentialsLogin credentialsLogin) {
        Optional<Credentials> credentials = this.credentialsRepository.findOneByUsername(credentialsLogin.getUsername());

        if (credentials.isEmpty()
                || !this.passwordEncoder.matches(
                        credentialsLogin.getPassword(),
                        credentials.get().getPasswordHash()
                )
        ) {
            return EndpointResponse.failed("Invalid credentials!");
        }

        String userId = credentials.get().getUserId();

        Optional<WorkerProfile> workerProfile = this.profileGenerator.getWorkerOnlyProfile(userId);
        Instant dateRightNow = new Date().toInstant();

        if (workerProfile.isPresent() && (
                workerProfile.get().isBanned()
                        || (workerProfile.get().getSuspension() != null
                        && workerProfile.get().getSuspension().toInstant().isAfter(dateRightNow))
        )) {
            return EndpointResponse.failed("Worker cannot access platform!");
        }

        Optional<Sessions> existingSession = this.sessionsRepository.findByUserId(userId);

        return EndpointResponse.passed(
                this.populateLoginData(
                        userId,
                        existingSession.map(Sessions::getToken).orElse(null)
                )
        );
    }

    @PostMapping("/logout")
    public EndpointResponse<Boolean> logout() {
        Optional<Sessions> sessions = this.getSession();
        if (sessions.isEmpty()) {
            return EndpointResponse.failed("Invalid session!");
        }

        this.sessionsRepository.deleteByToken(sessions.get().getToken());
        return EndpointResponse.passed(true);
    }
}
