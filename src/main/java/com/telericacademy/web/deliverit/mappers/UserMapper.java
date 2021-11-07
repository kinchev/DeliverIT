package com.telericacademy.web.deliverit.mappers;


import com.telericacademy.web.deliverit.models.Address;
import com.telericacademy.web.deliverit.models.Role;
import com.telericacademy.web.deliverit.models.User;
import com.telericacademy.web.deliverit.models.UserDto;
import com.telericacademy.web.deliverit.repositories.AddressRepository;
import com.telericacademy.web.deliverit.repositories.RoleRepository;
import com.telericacademy.web.deliverit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserMapper {

    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserMapper(UserRepository userRepository, AddressRepository addressRepository,
                      RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.roleRepository = roleRepository;
    }

    public User fromDto(UserDto userDto) {
        User user = new User();
        dtoToObject(userDto, user);
        return user;
    }

    public User fromDto(UserDto userDto, int id) {
        User user = userRepository.getById(id);
        dtoToObject(userDto, user);
        return user;
    }

    private void dtoToObject(UserDto userDto, User user) {
        Address address = addressRepository.getById(userDto.getAddressId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setAddress(address);
        user.setRoles(Set.of(roleRepository.getById(1)));
    }

}
