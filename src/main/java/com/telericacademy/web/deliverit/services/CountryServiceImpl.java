package com.telericacademy.web.deliverit.services;

import com.telericacademy.web.deliverit.models.Country;
import com.telericacademy.web.deliverit.repositories.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

   private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }


    @Override
    public List<Country> getAll() {
        return countryRepository.getAll();
    }

    @Override
    public Country getById(int id) {
        return countryRepository.getById(id);
    }
}
