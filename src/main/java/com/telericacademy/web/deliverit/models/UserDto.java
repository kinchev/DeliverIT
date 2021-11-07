package com.telericacademy.web.deliverit.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class UserDto {

    @NotBlank(message = "First name should not be empty.")
    @Size(min = 2, max = 20, message = "First name should be between 2 and 20 symbols.")
    private String firstName;

    @NotBlank(message = "Last name should not be empty.")
    @Size(min = 2, max = 20, message = "Last name should be between 2 and 20 symbols.")
    private String lastName;

    @NotNull
    @NotBlank(message = "Email should not be empty.")
    @Size(min = 2, max = 50, message = "Email should be between 2 and 50 symbols.")
    private String email;

    @Positive(message = "Address id should be positive.")
    private int addressId;

    public UserDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
}
