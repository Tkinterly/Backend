package com.tinkerly.tinkerly.payloads;

import com.tinkerly.tinkerly.entities.WorkerProfiles;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class WorkerProfile {
    int yearsOfExperience;
    Date suspension;
    boolean banned;
    List<Integer> workerDomains;
    List<String> workerEducation;
    List<String> workerSkills;

    public WorkerProfile(
            WorkerProfiles workerProfile,
            List<Integer> workerDomains,
            List<String> workerEducation,
            List<String> workerSkills
    ) {
        this.yearsOfExperience = workerProfile.getYearsOfExperience();
        this.suspension = workerProfile.getSuspension();
        this.banned = workerProfile.isBanned();
        this.workerDomains = workerDomains;
        this.workerEducation = workerEducation;
        this.workerSkills = workerSkills;
    }

    public WorkerProfile(
        int yearsOfExperience,
        List<Integer> workerDomains,
        List<String> workerEducation,
        List<String> workerSkills
    ) {
        this.yearsOfExperience = yearsOfExperience;
        this.suspension = null;
        this.banned = false;
        this.workerDomains = workerDomains;
        this.workerEducation = workerEducation;
        this.workerSkills = workerSkills;
    }

    public WorkerProfile() {}
}
