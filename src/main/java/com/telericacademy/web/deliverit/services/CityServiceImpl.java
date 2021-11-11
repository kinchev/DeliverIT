package com.telericacademy.web.deliverit.services;

import com.telericacademy.web.deliverit.models.City;
import com.telericacademy.web.deliverit.repositories.contracts.CityRepository;
import com.telericacademy.web.deliverit.services.contracts.CityService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<City> getAll() {
        return cityRepository.getAll();
    }

    @Override
    public City getById(int id) {
        return cityRepository.getById(id);
    }
}
