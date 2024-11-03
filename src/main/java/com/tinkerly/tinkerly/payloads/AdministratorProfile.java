package com.tinkerly.tinkerly.payloads;

import com.tinkerly.tinkerly.entities.AdministratorProfiles;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorProfile {
    boolean banAuthority;

    public AdministratorProfile(AdministratorProfiles administratorProfile) {
        this.banAuthority = administratorProfile.isBanAuthority();
    }

    public AdministratorProfile() {}
}
