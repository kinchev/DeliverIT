package com.telericacademy.web.deliverit.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ShowCountryDto {
    @Positive(message="Country id positive ")
    private int id;

    @NotNull(message = "Country name can`t be null")
    private String countryName;
}
