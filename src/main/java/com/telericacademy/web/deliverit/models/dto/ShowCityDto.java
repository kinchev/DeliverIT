package com.telericacademy.web.deliverit.models.dto;


import javax.validation.constraints.Positive;

public class ShowCityDto {
    @Positive(message="City id positive ")
    private int id;

    private String townName;

    private String countryName;

    public ShowCityDto() {
    }

    public ShowCityDto(int id, String townName, String countryName) {
        this.id = id;
        this.townName = townName;
        this.countryName = countryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
