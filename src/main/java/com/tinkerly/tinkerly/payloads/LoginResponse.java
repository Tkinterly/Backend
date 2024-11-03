package com.tinkerly.tinkerly.payloads;

import com.tinkerly.tinkerly.entities.WorkDetails;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LoginResponse {
    Profile profile;
    List<WorkDetails> workDetails;
    String token;

    public LoginResponse(Profile profile, List<WorkDetails> workDetails, String token) {
        this.profile = profile;
        this.workDetails = workDetails;
        this.token = token;
    }

    public LoginResponse() {}
}
