package com.telericacademy.web.deliverit.controllers;

import com.telericacademy.web.deliverit.exceptions.DuplicateEntityException;
import com.telericacademy.web.deliverit.exceptions.EntityNotFoundException;
import com.telericacademy.web.deliverit.exceptions.UnauthorizedOperationException;
import com.telericacademy.web.deliverit.mappers.ShipmentMapper;
import com.telericacademy.web.deliverit.mappers.ShowShipmentMapper;
import com.telericacademy.web.deliverit.models.*;
import com.telericacademy.web.deliverit.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {
    private final ShipmentService shipmentService;
    private final ShowShipmentMapper showShipmentMapper;
    private final ShipmentMapper shipmentMapper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public ShipmentController(ShipmentService shipmentService, ShowShipmentMapper showShipmentMapper, ShipmentMapper shipmentMapper, AuthenticationHelper authenticationHelper) {
        this.shipmentService = shipmentService;
        this.showShipmentMapper = showShipmentMapper;
        this.shipmentMapper = shipmentMapper;
        this.authenticationHelper = authenticationHelper;
    }


    @GetMapping
    public List<Shipment> getAll(@RequestHeader HttpHeaders headers) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return shipmentService.getAll(user);
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }


    }


    @GetMapping("/{id}")
    public Shipment getById(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return shipmentService.getById(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping

    public Shipment create(@RequestHeader HttpHeaders headers, @Valid @RequestBody ShipmentDto shipmentDTO) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Shipment shipment = showShipmentMapper.shipmentFromDto(shipmentDTO);
            shipmentService.create(shipment, user);
            return shipment;
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{id}")

    public Shipment update(@RequestHeader HttpHeaders headers, @PathVariable int id, @Valid @RequestBody ShipmentDto shipmentDTO) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Shipment shipment = shipmentMapper.fromDto(shipmentDTO, id);
            shipmentService.update(shipment, user);
            return shipment;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

//    @GetMapping("/smart")
//    public List<ShowShipmentDto> getAllSmart() {
//        return shipmentService.getAll().stream()
//                .map(showShipmentMapper::showShipment)
//                .collect(Collectors.toList());
//    }

    @GetMapping("/filterWarehouse")
    public List<Shipment> filterByWarehouse(@RequestHeader HttpHeaders headers, int warehouseId) {
        try {
            User user = authenticationHelper.tryGetUser(headers);

            return shipmentService.filterByWarehouse(warehouseId);
        } catch (
                UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

    }

    @GetMapping("/filterCustomer")
    public List<Shipment> filterByCustomer(@RequestHeader HttpHeaders headers, int customerId) {
        try {
            User user = authenticationHelper.tryGetUser(headers);

            return shipmentService.filterByCustomer(customerId);
        } catch (
                UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);

            shipmentService.delete(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

    }

}