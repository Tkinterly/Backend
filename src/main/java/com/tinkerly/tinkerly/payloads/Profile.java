package com.tinkerly.tinkerly.payloads;

import com.tinkerly.tinkerly.entities.Profiles;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Profile {
    String userId;
    String avatarId;
    Date registrationDate;
    int access;
    float averageRating;
    UserDetail userDetails;
    AdministratorProfile administratorProfile;
    CustomerProfile customerProfile;
    WorkerProfile workerProfile;

    public Profile(
            Profiles profile,
            UserDetail userDetails,
            AdministratorProfile administratorProfile,
            CustomerProfile customerProfile,
            WorkerProfile workerProfile
    ) {
        this.userId = profile.getUserId();
        this.avatarId = profile.getAvatarId();
        this.registrationDate = profile.getRegistrationDate();
        this.access = profile.getAccess();
        this.averageRating = profile.getAverageRating();

        this.userDetails = userDetails;
        this.administratorProfile = administratorProfile;
        this.customerProfile = customerProfile;
        this.workerProfile = workerProfile;
    }

    public Profile() {}
}
