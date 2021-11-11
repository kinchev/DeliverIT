package com.telericacademy.web.deliverit.mappers;


import com.telericacademy.web.deliverit.models.*;
import com.telericacademy.web.deliverit.models.dto.RegisterDto;
import com.telericacademy.web.deliverit.models.dto.UserDto;
import com.telericacademy.web.deliverit.models.dto.UserInDto;
import com.telericacademy.web.deliverit.repositories.contracts.AddressRepository;
import com.telericacademy.web.deliverit.repositories.contracts.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserMapper {

    private UserRepository userRepository;
    private AddressRepository addressRepository;

    @Autowired
    public UserMapper(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public User fromInDto(UserInDto userInDto, int addressId) {
        User user = new User();
        InDtoToObject(userInDto, user, addressId);
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
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setAddress(address);
        user.setRoles(new HashSet<>());
    }

    private void InDtoToObject(UserInDto userInDto, User user, int addressId) {
        Address address = addressRepository.getById(addressId);
        user.setFirstName(userInDto.getFirstName());
        user.setLastName(userInDto.getLastName());
        user.setPassword(userInDto.getPassword());
        user.setEmail(userInDto.getEmail());
        user.setAddress(address);
        user.setRoles(new HashSet<>());
    }

    public User fromDto(RegisterDto registerDto) {
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());

        Role role = new Role();
        role.setId(1);
        role.setName("User");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        return user;
    }
}
