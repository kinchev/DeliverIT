package com.telericacademy.web.deliverit.services;

import com.telericacademy.web.deliverit.models.Address;

import com.telericacademy.web.deliverit.repositories.contracts.AddressRepository;
import com.telericacademy.web.deliverit.repositories.contracts.CityRepository;
import com.telericacademy.web.deliverit.services.contracts.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, CityRepository cityRepository) {
        this.addressRepository = addressRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public Address getById(int id) {
        return addressRepository.getById(id);
    }

    @Override
    public void createFromUser(Address address) {
        addressRepository.create(address);
    }


}
