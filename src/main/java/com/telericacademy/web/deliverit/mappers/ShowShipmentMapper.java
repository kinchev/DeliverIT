package com.telericacademy.web.deliverit.mappers;

import com.telericacademy.web.deliverit.models.Shipment;
import com.telericacademy.web.deliverit.models.ShipmentDto;
import com.telericacademy.web.deliverit.models.ShowShipmentDto;
import com.telericacademy.web.deliverit.models.Warehouse;
import com.telericacademy.web.deliverit.repositories.WarehouseRepository;
import org.springframework.stereotype.Component;

@Component
public class ShowShipmentMapper {

    WarehouseRepository warehouseRepository;

    public ShowShipmentMapper(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public Shipment shipmentFromDto(ShipmentDto shipmentDTO) {
        Shipment shipment = new Shipment();
        return shipmentFromDto(shipment, shipmentDTO);
    }


    public Shipment shipmentFromDto(Shipment shipment, ShipmentDto shipmentDto) {
        Warehouse originWarehouse = warehouseRepository.getById(shipmentDto.getOriginWarehouseId());
        Warehouse destinationWarehouse = warehouseRepository.getById(shipmentDto.getDestinationWarehouseId());

        if (shipmentDto.getDepartureDate() != null) {
            shipment.setDepartureDate(shipmentDto.getDepartureDate());
        }
        if (shipmentDto.getArrivalDate() != null) {
            shipment.setArrivalDate(shipmentDto.getArrivalDate());
        }

        shipment.setOriginWarehouseId(originWarehouse);
        shipment.setDestinationWarehouseId(destinationWarehouse);

        return shipment;

    }

    public ShowShipmentDto showShipment(Shipment shipment) {
        ShowShipmentDto showShipmentDto = new ShowShipmentDto();
        showShipmentDto.setId(shipment.getId());

        showShipmentDto.setOriginWarehouse(
                String.format("Id:%d , Address: %s, %s, %s",
                        shipment.getOriginWarehouseId().getId(),
                        shipment.getOriginWarehouseId().getAddress().getStreetName(),
                        shipment.getOriginWarehouseId().getAddress().getCity().getCityName(),
                        shipment.getOriginWarehouseId().getAddress().getCity().getCountry().getCountryName()));


        showShipmentDto.setDestinationWarehouse(
                String.format("Id:%d , Address: %s, %s, %s",
                        shipment.getOriginWarehouseId().getId(),
                        shipment.getDestinationWarehouseId().getAddress().getStreetName(),
                        shipment.getDestinationWarehouseId().getAddress().getCity().getCityName(),
                        shipment.getDestinationWarehouseId().getAddress().getCity().getCountry().getCountryName()));


        showShipmentDto.setStatus(shipment.getStatus());
        return showShipmentDto;
    }

    private void setShowShipmentDates(ShowShipmentDto showShipmentDto, Shipment shipment) {
        if (shipment.getArrivalDate() != null) {
            showShipmentDto.setArrivalDate(shipment.getArrivalDate().toString());
        } else {
            showShipmentDto.setArrivalDate("Arrival date to be confirmed.");
        }

        if (shipment.getDepartureDate() != null) {
            showShipmentDto.setDepartureDate(shipment.getArrivalDate().toString());
        } else {
            showShipmentDto.setDepartureDate("Departure date to be confirmed.");
        }


    }

}
