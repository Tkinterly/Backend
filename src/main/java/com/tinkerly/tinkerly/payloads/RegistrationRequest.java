package com.tinkerly.tinkerly.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    Profile userProfile;
    String username;
    String password;

    public RegistrationRequest() {}
}
