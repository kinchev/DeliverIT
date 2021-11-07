package com.telericacademy.web.deliverit.mappers;

import com.telericacademy.web.deliverit.models.Shipment;
import com.telericacademy.web.deliverit.models.ShipmentDto;
import com.telericacademy.web.deliverit.models.Warehouse;
import com.telericacademy.web.deliverit.repositories.ShipmentRepository;
import com.telericacademy.web.deliverit.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        shipment.setOriginWarehouseId(originWarehouse);
        shipment.setDestinationWarehouseId(destinationWarehouse);
        shipment.setArrivalDate(shipmentDTO.getArrivalDate());
        shipment.setDepartureDate(shipmentDTO.getDepartureDate());
    }

}
