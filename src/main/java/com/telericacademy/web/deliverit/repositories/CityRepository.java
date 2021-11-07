package com.telericacademy.web.deliverit.repositories;

import com.telericacademy.web.deliverit.models.City;

import java.util.List;

public interface CityRepository {
    List<City> getAll();

    City getById(int id);
}
