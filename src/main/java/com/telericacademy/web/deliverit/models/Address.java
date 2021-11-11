package com.telericacademy.web.deliverit.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @NotBlank
    @Size(min = 2, max = 30, message = "Street name should be with length between 2 and 30 characters.")
    @Column(name = "street_name")
    private String streetName;

    public Address() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return getId() == address.getId() &&
                Objects.equals(getCity(), address.getCity()) &&
                getStreetName().equals(address.getStreetName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCity(), getStreetName());
    }

}