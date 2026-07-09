package com.vithu.apexpay.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateAccountRequest {

    @NotBlank(message = "Owner name is required")
    @Size(min = 2, max = 50, message = "Owner name must be 2-50 characters")
    private String ownerName;

    public CreateAccountRequest() {}

    public CreateAccountRequest(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
}
