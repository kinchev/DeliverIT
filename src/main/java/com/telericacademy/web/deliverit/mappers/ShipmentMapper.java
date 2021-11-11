package com.telericacademy.web.deliverit.mappers;

import com.telericacademy.web.deliverit.models.*;
import com.telericacademy.web.deliverit.models.dto.ShipmentDto;
import com.telericacademy.web.deliverit.repositories.contracts.ShipmentRepository;
import com.telericacademy.web.deliverit.repositories.contracts.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class ShipmentMapper {
    private ShipmentRepository shipmentRepository;
    private WarehouseRepository warehouseRepository;

    @Autowired
    public ShipmentMapper(ShipmentRepository shipmentRepository, WarehouseRepository warehouseRepository) {
        this.shipmentRepository = shipmentRepository;
        this.warehouseRepository = warehouseRepository;
    }

    public Shipment fromDto(ShipmentDto shipmentDTO) {
        Shipment shipment = new Shipment();
        dtoToObject(shipmentDTO, shipment);
        return shipment;
    }

    public Shipment fromDto(ShipmentDto shipmentDTO, int id) {
        Shipment shipment = shipmentRepository.getById(id);
        dtoToObject(shipmentDTO, shipment);
        return shipment;
    }

    private void dtoToObject(ShipmentDto shipmentDTO, Shipment shipment) {
        Warehouse originWarehouse = warehouseRepository.getById(shipmentDTO.getOriginWarehouseId());
        Warehouse destinationWarehouse = warehouseRepository.getById(shipmentDTO.getDestinationWarehouseId());
        shipment.setOriginWarehouse(originWarehouse);
        shipment.setDestinationWarehouse(destinationWarehouse);
        shipment.setArrivalDate(shipmentDTO.getArrivalDate());
        shipment.setDepartureDate(shipmentDTO.getDepartureDate());
        shipment.setParcels(new HashSet<>());
    }

}
