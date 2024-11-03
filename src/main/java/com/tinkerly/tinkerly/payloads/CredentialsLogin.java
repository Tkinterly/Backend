package com.tinkerly.tinkerly.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialsLogin {
    String username;
    String password;

    public CredentialsLogin() {}
}
