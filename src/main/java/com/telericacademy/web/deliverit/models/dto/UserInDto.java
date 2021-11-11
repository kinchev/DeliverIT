package com.telericacademy.web.deliverit.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class UserInDto {

    @NotBlank(message = "First name should not be empty.")
    @Size(min = 2, max = 20, message = "First name should be between 2 and 20 symbols.")
    private String firstName;

    @NotBlank(message = "Last name should not be empty.")
    @Size(min = 2, max = 20, message = "Last name should be between 2 and 20 symbols.")
    private String lastName;

    @NotBlank(message = "Password should not be empty.")
    @Size(min = 2, max = 20, message = "Username should be between 2 and 20 symbols.")
    private String password;

    @NotNull
    @NotBlank(message = "Email should not be empty.")
    @Size(min = 2, max = 50, message = "Email should be between 2 and 50 symbols.")
    private String email;

    @NotBlank(message = "Street name should not be empty.")
    @Size(min = 2, max = 20, message = "Street name should be between 2 and 20 symbols.")
    private String streetName;

    @Positive(message = "City id should be positive.")
    private int cityId;

    public UserInDto() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}

