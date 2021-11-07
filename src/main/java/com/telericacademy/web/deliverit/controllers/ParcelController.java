package com.telericacademy.web.deliverit.controllers;

import com.telericacademy.web.deliverit.exceptions.DuplicateEntityException;
import com.telericacademy.web.deliverit.exceptions.EntityNotFoundException;
import com.telericacademy.web.deliverit.exceptions.UnauthorizedOperationException;
import com.telericacademy.web.deliverit.mappers.ParcelMapper;
import com.telericacademy.web.deliverit.models.Parcel;
import com.telericacademy.web.deliverit.models.ParcelDto;
import com.telericacademy.web.deliverit.models.User;
import com.telericacademy.web.deliverit.services.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parcels")

public class ParcelController {

    private final ParcelService parcelService;
    private final ParcelMapper parcelMapper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public ParcelController(ParcelService parcelService, ParcelMapper parcelMapper,
                            AuthenticationHelper authenticationHelper) {
        this.parcelService = parcelService;
        this.parcelMapper = parcelMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<Parcel> getAll(@RequestHeader HttpHeaders headers) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return parcelService.getAll(user);
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/filter")
    public List<Parcel> filter(@RequestHeader HttpHeaders headers,
                               @RequestParam(required = false) Optional<Double> minWeight,
                               @RequestParam(required = false) Optional<String> lastName,
                               @RequestParam(required = false) Optional<Integer> originWarehouseId,
                               @RequestParam(required = false) Optional<Integer> destinationWarehouseId,
                               @RequestParam(required = false) Optional<Integer> categoryId,
                               @RequestParam(required = false) Optional<String> sort
    ) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return parcelService.filter(user, minWeight, lastName, originWarehouseId, destinationWarehouseId, categoryId, sort);
        } catch (
                UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Parcel getById(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return parcelService.getById(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping
    public Parcel create(@RequestHeader HttpHeaders headers, @Valid @RequestBody ParcelDto parcelDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Parcel parcel = parcelMapper.fromDto(parcelDto);
            parcelService.create(parcel, user);
            return parcel;
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Parcel update(@RequestHeader HttpHeaders headers, @PathVariable int id, @Valid @RequestBody ParcelDto parcelDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Parcel parcel = parcelMapper.fromDto(parcelDto, id);
            parcelService.update(parcel, user);
            return parcel;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            parcelService.delete(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}


