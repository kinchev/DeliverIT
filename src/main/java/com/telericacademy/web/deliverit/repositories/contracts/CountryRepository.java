package com.telericacademy.web.deliverit.repositories.contracts;



import com.telericacademy.web.deliverit.models.Country;

import java.util.List;

public interface CountryRepository {
    List<Country> getAll();

    Country getById(int id);

}
