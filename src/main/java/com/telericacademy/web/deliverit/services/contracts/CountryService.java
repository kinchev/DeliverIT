package com.telericacademy.web.deliverit.services.contracts;

import com.telericacademy.web.deliverit.models.Country;
import com.telericacademy.web.deliverit.models.User;
import com.telericacademy.web.deliverit.models.Warehouse;

import java.util.List;

public interface CountryService {
    List<Country> getAll();

    Country getById(int id);

}
