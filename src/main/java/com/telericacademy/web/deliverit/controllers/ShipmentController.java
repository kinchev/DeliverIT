package com.telericacademy.web.deliverit.controllers;

import com.telericacademy.web.deliverit.exceptions.DuplicateEntityException;
import com.telericacademy.web.deliverit.exceptions.EntityNotFoundException;
import com.telericacademy.web.deliverit.mappers.ShipmentMapper;
import com.telericacademy.web.deliverit.mappers.ShowShipmentMapper;
import com.telericacademy.web.deliverit.models.Shipment;
import com.telericacademy.web.deliverit.models.ShipmentDto;
import com.telericacademy.web.deliverit.models.ShowShipmentDto;
import com.telericacademy.web.deliverit.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {
    private final ShipmentService shipmentService;
    private final ShowShipmentMapper showShipmentMapper;
    private final ShipmentMapper shipmentMapper;

    @Autowired
    public ShipmentController(ShipmentService shipmentService, ShowShipmentMapper showShipmentMapper, ShipmentMapper shipmentMapper) {
        this.shipmentService = shipmentService;
        this.showShipmentMapper = showShipmentMapper;
        this.shipmentMapper = shipmentMapper;
    }



    @GetMapping
    public List<Shipment> getAll() {


        return shipmentService.getAll();
    }



    @GetMapping("/{id}")
    public Shipment getById(@PathVariable int id) {
        try {
            return shipmentService.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping

    public Shipment create(@Valid @RequestBody ShipmentDto shipmentDTO) {
        try {
            Shipment shipment = showShipmentMapper.shipmentFromDto(shipmentDTO);
            shipmentService.create(shipment);
            return shipment;
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}")

    public Shipment update(@PathVariable int id, @Valid @RequestBody ShipmentDto shipmentDTO) {
        try {
            Shipment shipment = shipmentMapper.fromDto(shipmentDTO, id);
            shipmentService.update(shipment);
            return shipment;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
    @GetMapping("/smart")
    public List<ShowShipmentDto> getAllSmart() {
        return shipmentService.getAll().stream()
                .map(showShipmentMapper::showShipment)
                .collect(Collectors.toList());
    }

    @GetMapping("/filter")
    public List<Shipment> filterByWarehouse(int warehouseId) {

        return shipmentService.filterByWarehouse(warehouseId);
    }

//@GetMapping("/filter1")
//public List<Shipment> filter1(@RequestParam(required = false) Optional<Integer> originWarehouseId,Optional<Integer> destinationWarehouseId){
//        return shipmentService.filter1(originWarehouseId,destinationWarehouseId);
//}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        try {
            shipmentService.delete(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}