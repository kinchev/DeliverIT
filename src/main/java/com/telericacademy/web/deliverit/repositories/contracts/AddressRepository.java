package com.telericacademy.web.deliverit.repositories.contracts;

import com.telericacademy.web.deliverit.models.Address;

public interface AddressRepository {

    Address getById (int id);

    void create (Address address);
}
