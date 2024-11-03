package com.tinkerly.tinkerly.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorAction {
    String administratorId;
    String reportId;
    int actionType;

    public AdministratorAction() {}
}
