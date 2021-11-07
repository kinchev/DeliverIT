package com.telericacademy.web.deliverit.controllers;

import com.telericacademy.web.deliverit.exceptions.DuplicateEntityException;
import com.telericacademy.web.deliverit.exceptions.EntityNotFoundException;
import com.telericacademy.web.deliverit.exceptions.UnauthorizedOperationException;
import com.telericacademy.web.deliverit.mappers.UserMapper;
import com.telericacademy.web.deliverit.models.Parcel;
import com.telericacademy.web.deliverit.models.User;
import com.telericacademy.web.deliverit.models.UserDto;
import com.telericacademy.web.deliverit.services.UserService;
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
    private final UserMapper userMapper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper,
                          AuthenticationHelper authenticationHelper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping()
    public List<User> getAll(@RequestHeader HttpHeaders headers) {
        authenticationHelper.tryGetUser(headers);
        return userService.getAll();
    }

    @GetMapping("/count")
    public Long countCustomers() {
        return userService.getAll().stream().filter(user -> !user.isEmployee())
                .count();
    }

    @GetMapping("/customers")
    public List<User> getAllCustomers(@RequestHeader HttpHeaders headers) {
        authenticationHelper.tryGetUser(headers);
        return userService.getAll().stream().filter(user -> !user.isEmployee())
                .collect(Collectors.toList());
    }

//     return userService.getAll().stream().filter1(user -> user.getRoles().stream().
//    allMatch(r -> r.getCityName().equals("customer"))).collect(Collectors.toList());

    @GetMapping("/{id}")
    public User getById(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            authenticationHelper.tryGetUser(headers);
            return userService.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/email/{email}")
    public User getByEmail(@RequestHeader HttpHeaders headers, @PathVariable String email) {
        try {
            authenticationHelper.tryGetUser(headers);
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
            return userService.search(email, firstName, lastName);
        } catch (
                UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("{id}/parcels")
    public List<Parcel> getUserParcels(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = getById(headers, id);
            return userService.getUserParcels(user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @GetMapping("/{id}/incoming-parcels")
    public List<Parcel> incomingParcels(@RequestHeader HttpHeaders headers,@PathVariable int id){
        try {
            User user = getById(headers, id);
            return userService.getUserParcels(user);
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @PostMapping
    public User create(@Valid @RequestBody UserDto userDto) {
        try {
            User user = userMapper.fromDto(userDto);
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


}
