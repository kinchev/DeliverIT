package com.telericacademy.web.deliverit.controllers.rest;

import com.telericacademy.web.deliverit.exceptions.EntityNotFoundException;
import com.telericacademy.web.deliverit.models.City;
import com.telericacademy.web.deliverit.models.dto.ShowCityDto;
import com.telericacademy.web.deliverit.services.contracts.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cities")
public class CityController {

private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }


    @GetMapping
    public List<City> getAll() {
        return cityService.getAll();


    }

    @GetMapping("/info")
    public List<ShowCityDto> getAllInfo() {
        return cityService.getAll().stream()
                .map(city -> {
                    ShowCityDto dto = new ShowCityDto();
                    dto.setId(city.getId());
                    dto.setTownName(city.getCityName());
                    dto.setCountryName(city.getCountry().getCountryName());
                    return dto;
                }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public City getById(@PathVariable int id) {
        try {
            return cityService.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
