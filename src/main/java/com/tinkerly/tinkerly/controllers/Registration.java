package com.tinkerly.tinkerly.controllers;

import com.tinkerly.tinkerly.components.EndpointResponse;
import com.tinkerly.tinkerly.entities.*;
import com.tinkerly.tinkerly.payloads.Profile;
import com.tinkerly.tinkerly.payloads.RegistrationRequest;
import com.tinkerly.tinkerly.payloads.UserDetail;
import com.tinkerly.tinkerly.payloads.WorkerProfile;
import com.tinkerly.tinkerly.repositories.*;
import com.tinkerly.tinkerly.services.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class Registration {
    private final CredentialsRepository credentialsRepository;
    private final ProfileRepository profileRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final CustomerProfileRepository customerProfileRepository;
    private final WorkerProfileRepository workerProfileRepository;
    private final WorkerDomainsRepository workerDomainsRepository;
    private final WorkerEducationRepository workerEducationRepository;
    private final WorkerSkillsRepository workerSkillsRepository;

    PasswordEncoder passwordEncoder;

    public Registration(
            CredentialsRepository credentialsRepository,
            ProfileRepository profileRepository,
            UserDetailsRepository userDetailsRepository,
            CustomerProfileRepository customerProfileRepository,
            WorkerProfileRepository workerProfileRepository,
            WorkerDomainsRepository workerDomainsRepository,
            WorkerEducationRepository workerEducationRepository,
            WorkerSkillsRepository workerSkillsRepository
    ) {
        this.credentialsRepository = credentialsRepository;
        this.profileRepository = profileRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.customerProfileRepository = customerProfileRepository;
        this.workerProfileRepository = workerProfileRepository;
        this.workerDomainsRepository = workerDomainsRepository;
        this.workerEducationRepository = workerEducationRepository;
        this.workerSkillsRepository = workerSkillsRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/credentials/create")
    public EndpointResponse<Boolean> createCredentials(@RequestBody List<Credentials> credentials) {

        for (Credentials credential : credentials) {

            String passwordHash = this.passwordEncoder.encode(credential.getPasswordHash());

            Credentials credentialsEntry = new Credentials(
                    credential.getUserId(),
                    credential.getUsername(),
                    passwordHash
            );

            this.credentialsRepository.save(credentialsEntry);
        }

        return EndpointResponse.passed(true);
    }

    @PostMapping("/register")
    public EndpointResponse<String> register(@RequestBody RegistrationRequest registrationRequest) {
        String userId = UUID.randomUUID().toString();
        String passwordHash = this.passwordEncoder.encode(registrationRequest.getPassword());
        String username = registrationRequest.getUsername();

        if (this.credentialsRepository.existsByUsername(username)) {
            return EndpointResponse.failed("User already exists!");
        }

        Credentials credentialsEntry = new Credentials(userId, username, passwordHash);

        Profile userProfile = registrationRequest.getUserProfile();
        Profiles userProfileEntry = new Profiles(
                userId,
                Calendar.getInstance().getTime(),
                0,
                0
        );

        UserDetail userDetails = userProfile.getUserDetails();
        userDetails.setUserId(userId);
        UserDetails userDetailsEntry = new UserDetails(userDetails);

        this.credentialsRepository.save(credentialsEntry);
        this.profileRepository.save(userProfileEntry);
        this.userDetailsRepository.save(userDetailsEntry);

        if (userProfile.getCustomerProfile() != null) {
            CustomerProfiles customerProfileEntry = new CustomerProfiles(userId, 3);

            this.customerProfileRepository.save(customerProfileEntry);
        } else if (userProfile.getWorkerProfile() != null) {
            WorkerProfile workerProfile = userProfile.getWorkerProfile();
            WorkerProfiles workerProfileEntry = new WorkerProfiles(
                    userId,
                    workerProfile.getYearsOfExperience(),
                    null,
                    false
            );

            List<WorkerDomains> workerDomains = new ArrayList<>();
            List<WorkerEducation> workerEducations = new ArrayList<>();
            List<WorkerSkills> workerSkills = new ArrayList<>();

            for (int workerDomain : workerProfile.getWorkerDomains()) {
                workerDomains.add(new WorkerDomains(userId, workerDomain));
            }

            for (String workerEducation : workerProfile.getWorkerEducation()) {
                workerEducations.add(new WorkerEducation(userId, workerEducation));
            }

            for (String workerSkill : workerProfile.getWorkerSkills()) {
                workerSkills.add(new WorkerSkills(userId, workerSkill));
            }

            this.workerProfileRepository.save(workerProfileEntry);
            this.workerDomainsRepository.saveAll(workerDomains);
            this.workerEducationRepository.saveAll(workerEducations);
            this.workerSkillsRepository.saveAll(workerSkills);
        } else {
            return EndpointResponse.failed("Invalid profile data!");
        }

        return EndpointResponse.passed(userId);
    }
}
