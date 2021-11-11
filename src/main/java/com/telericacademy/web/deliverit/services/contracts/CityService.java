package com.telericacademy.web.deliverit.services.contracts;

import com.telericacademy.web.deliverit.models.City;
import com.telericacademy.web.deliverit.models.Country;

import java.util.List;

public interface CityService {
    List<City> getAll();

    City getById(int id);
}
