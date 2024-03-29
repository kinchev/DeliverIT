package com.telericacademy.web.deliverit.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private int id;

    @NotBlank
    @Size(min = 2, max = 30, message = "Country countryName should be with length between 2 and 30 characters.")
    @Column(name = "country_name")
    private String countryName;

    public Country() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return getId() == country.getId() &&
                getCountryName().equals(country.getCountryName());
    }

    @Override
    public int hashCode() {;
        return Objects.hash(getId(), getCountryName());
    }
}
