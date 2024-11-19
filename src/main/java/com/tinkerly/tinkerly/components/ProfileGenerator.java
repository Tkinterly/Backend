package com.tinkerly.tinkerly.components;

import com.tinkerly.tinkerly.entities.*;
import com.tinkerly.tinkerly.payloads.*;
import com.tinkerly.tinkerly.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProfileGenerator {
    private ProfileRepository profileRepository;
    private UserDetailsRepository userDetailsRepository;
    private AdministratorProfileRepository administratorProfileRepository;
    private CustomerProfileRepository customerProfileRepository;
    private WorkerProfileRepository workerProfileRepository;
    private WorkerDomainsRepository workerDomainsRepository;
    private WorkerEducationRepository workerEducationRepository;
    private WorkerSkillsRepository workerSkillsRepository;

    @Autowired
    public void setProfileRepository(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Autowired
    public void setUserDetailsRepository(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Autowired
    public void setAdministratorProfileRepository(AdministratorProfileRepository administratorProfileRepository) {
        this.administratorProfileRepository = administratorProfileRepository;
    }

    @Autowired
    public void setCustomerProfileRepository(CustomerProfileRepository customerProfileRepository) {
        this.customerProfileRepository = customerProfileRepository;
    }

    @Autowired
    public void setWorkerProfileRepository(WorkerProfileRepository workerProfileRepository) {
        this.workerProfileRepository = workerProfileRepository;
    }

    @Autowired
    public void setWorkerDomainsRepository(WorkerDomainsRepository workerDomainsRepository) {
        this.workerDomainsRepository = workerDomainsRepository;
    }

    @Autowired
    public void setWorkerEducationRepository(WorkerEducationRepository workerEducationRepository) {
        this.workerEducationRepository = workerEducationRepository;
    }

    @Autowired
    public void setSkillsRepository(WorkerSkillsRepository workerSkillsRepository) {
        this.workerSkillsRepository = workerSkillsRepository;
    }

    private Optional<Profile> getProfile(
            String userId,
            AdministratorProfile administratorProfile,
            CustomerProfile customerProfile,
            WorkerProfile workerProfile
    ) {
        Optional<Profiles> profileQuery = this.profileRepository.findById(userId);
        Optional<UserDetails> userDetailsQuery = this.userDetailsRepository.findByUserId(userId);

        if (profileQuery.isEmpty() || userDetailsQuery.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(
                new Profile(
                        profileQuery.get(),
                        new UserDetail(userDetailsQuery.get()),
                        administratorProfile,
                        customerProfile,
                        workerProfile
                )
        );
    }

    public Optional<AdministratorProfile> getAdministratorOnlyProfile(String userId) {
        Optional<AdministratorProfiles> administratorProfileQuery = this.administratorProfileRepository.findByUserId(userId);

        return administratorProfileQuery.map(AdministratorProfile::new);

    }

    public Optional<CustomerProfile> getCustomerOnlyProfile(String userId) {
        Optional<CustomerProfiles> customerProfileQuery = this.customerProfileRepository.findByUserId(userId);

        return customerProfileQuery.map(CustomerProfile::new);

    }

    public Optional<WorkerProfile> getWorkerOnlyProfile(String workerId) {
        Optional<WorkerProfiles> workerProfileQuery = this.workerProfileRepository.findByUserId(workerId);
        List<WorkerDomains> workerDomainsQuery = this.workerDomainsRepository.findAllByUserId(workerId);
        List<WorkerEducation> workerEducationQuery = this.workerEducationRepository.findAllByUserId(workerId);
        List<WorkerSkills> workerSkillsQuery = this.workerSkillsRepository.findAllByUserId(workerId);

        if (workerProfileQuery.isEmpty()) {
            return Optional.empty();
        }

        List<Integer> workerDomains = new ArrayList<>();
        List<String> workerEducations = new ArrayList<>();
        List<String> workerSkills = new ArrayList<>();

        for (WorkerDomains workerDomain : workerDomainsQuery) {
            workerDomains.add(workerDomain.toValue());
        }

        for (WorkerEducation workerEducation : workerEducationQuery) {
            workerEducations.add(workerEducation.toString());
        }

        for (WorkerSkills workerSkill : workerSkillsQuery) {
            workerSkills.add(workerSkill.toString());
        }

        return Optional.of(
                new WorkerProfile(
                    workerProfileQuery.get(),
                    workerDomains,
                    workerEducations,
                    workerSkills
            )
        );
    }

    public Optional<Profile> getAdministratorProfile(String administratorId) {
        Optional<AdministratorProfile> administratorOnlyProfile = this.getAdministratorOnlyProfile(administratorId);

        if (administratorOnlyProfile.isEmpty()) {
            return Optional.empty();
        }

        return this.getProfile(administratorId, administratorOnlyProfile.get(), null, null);
    }

    public Optional<Profile> getCustomerProfile(String customerId) {
        Optional<CustomerProfile> customerOnlyProfile = this.getCustomerOnlyProfile(customerId);

        if (customerOnlyProfile.isEmpty()) {
            return Optional.empty();
        }

        return this.getProfile(customerId, null, customerOnlyProfile.get(), null);
    }

    public Optional<Profile> getWorkerProfile(String workerId) {
        Optional<WorkerProfile> workerOnlyProfile = this.getWorkerOnlyProfile(workerId);

        if (workerOnlyProfile.isEmpty()) {
            return Optional.empty();
        }

        return this.getProfile(workerId, null, null, workerOnlyProfile.get());
    }

    public Optional<Profile> getGenericProfile(String userId) {
        Optional<AdministratorProfile> administratorOnlyProfile = this.getAdministratorOnlyProfile(userId);
        Optional<CustomerProfile> customerOnlyProfile = this.getCustomerOnlyProfile(userId);
        Optional<WorkerProfile> workerOnlyProfile = this.getWorkerOnlyProfile(userId);

        if (administratorOnlyProfile.isEmpty() && customerOnlyProfile.isEmpty() && workerOnlyProfile.isEmpty()) {
            return Optional.empty();
        }

        return this.getProfile(
                userId,
                administratorOnlyProfile.orElse(null),
                customerOnlyProfile.orElse(null),
                workerOnlyProfile.orElse(null)
        );
    }
}
