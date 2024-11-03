package com.tinkerly.tinkerly.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table()
public class AdministratorProfiles {
    @Id
    private String userId;

    @Column(nullable = false)
    private boolean banAuthority;

    protected AdministratorProfiles() {}

    public AdministratorProfiles(String userId, boolean banAuthority) {
        this.userId = userId;
        this.banAuthority = banAuthority;
    }
}
