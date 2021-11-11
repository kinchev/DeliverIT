package com.telericacademy.web.deliverit.services.contracts;

import com.telericacademy.web.deliverit.models.Address;
import com.telericacademy.web.deliverit.models.dto.UserInDto;

public interface AddressService {

    Address getById(int id);

    void createFromUser(Address address);


}
