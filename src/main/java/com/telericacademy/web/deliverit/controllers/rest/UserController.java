package com.telericacademy.web.deliverit.controllers.rest;

import com.telericacademy.web.deliverit.exceptions.DuplicateEntityException;
import com.telericacademy.web.deliverit.exceptions.EntityNotFoundException;
import com.telericacademy.web.deliverit.exceptions.UnauthorizedOperationException;
import com.telericacademy.web.deliverit.mappers.UserMapper;
import com.telericacademy.web.deliverit.models.Address;
import com.telericacademy.web.deliverit.models.Parcel;
import com.telericacademy.web.deliverit.models.Status;
import com.telericacademy.web.deliverit.models.User;
import com.telericacademy.web.deliverit.models.dto.UserDto;
import com.telericacademy.web.deliverit.models.dto.UserInDto;
import com.telericacademy.web.deliverit.repositories.contracts.UserRepository;
import com.telericacademy.web.deliverit.services.contracts.AddressService;
import com.telericacademy.web.deliverit.services.contracts.CityService;
import com.telericacademy.web.deliverit.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final AddressService addressService;
    private final CityService cityService;
    private final UserMapper userMapper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public UserController(UserService userService, AddressService addressService, CityService cityService,
                          UserMapper userMapper, AuthenticationHelper authenticationHelper) {
        this.userService = userService;
        this.addressService = addressService;
        this.cityService = cityService;
        this.userMapper = userMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping("/count")
    public Long countCustomers() {
        return userService.countCustomers();
    }

    @GetMapping("/customers")
    public List<User> getAllCustomers(@RequestHeader HttpHeaders headers) {
        User user = authenticationHelper.tryGetUser(headers);
        return userService.getAllUsers(user).stream().filter(u -> !u.isEmployee())
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public User getById(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return userService.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/email/{email}")
    public User getByEmail(@RequestHeader HttpHeaders headers, @PathVariable String email) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return userService.getByEmail(email);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/search")
    public List<User> search(@RequestHeader HttpHeaders headers,
                             @RequestParam(required = false) Optional<String> email,
                             @RequestParam(required = false) Optional<String> firstName,
                             @RequestParam(required = false) Optional<String> lastName
    ) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return userService.search(user, email, firstName, lastName);
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("{id}/parcels")
    public List<Parcel> getUserParcels(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User loggedUser = authenticationHelper.tryGetUser(headers);
            User user = userService.getById(id);
            return userService.getUserParcels(user, loggedUser);
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/{id}/parcels/filter")
    public List<Parcel> filterUserParcels(@RequestHeader HttpHeaders headers,
                                          @PathVariable int id,
                                          @RequestParam(required = false) Optional<String> delivered,
                                          @RequestParam(required = false) Optional<String> preparing,
                                          @RequestParam(required = false) Optional<String> incoming
    ) {
        try {
            User loggedUser = authenticationHelper.tryGetUser(headers);
            User user = userService.getById(id);
            return userService.filterUserParcels(user, loggedUser, delivered, preparing, incoming);
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping
    public User create(@Valid @RequestBody UserInDto userInDto) {
        try {
            Address address = createFromUser(userInDto);
            User user = userMapper.fromInDto(userInDto, address.getId());
            userService.create(user);
            return user;
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public User update(@RequestHeader HttpHeaders headers, @PathVariable int id, @Valid @RequestBody UserDto userDto) {
        try {
            User user = userMapper.fromDto(userDto, id);
            User updatingUser = authenticationHelper.tryGetUser(headers);
            userService.update(user, updatingUser);
            return user;
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User updatingUser = authenticationHelper.tryGetUser(headers);
            userService.delete(id, updatingUser);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("status/{parcelId}")
    public Status getParcelStatus(@RequestHeader HttpHeaders headers, @PathVariable int parcelId) {
        try {
            User loggedUser = authenticationHelper.tryGetUser(headers);
            return userService.getParcelStatus(loggedUser, parcelId);
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("status/{parcelId}")
    public Parcel setParcelStatus(@RequestHeader HttpHeaders headers, @PathVariable int parcelId,
                                  @RequestParam boolean deliverToUser) {
        try {
            User loggedUser = authenticationHelper.tryGetUser(headers);
            return userService.setParcelStatus(loggedUser, parcelId, deliverToUser);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }


    public Address createFromUser(UserInDto userInDto) {
        Address address = new Address();
        address.setStreetName(userInDto.getStreetName());
        address.setCity(cityService.getById(userInDto.getCityId()));
        addressService.createFromUser(address);
        return address;
    }
}
