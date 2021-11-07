package com.telericacademy.web.deliverit.services;

import com.telericacademy.web.deliverit.exceptions.InvalidUserInputException;
import com.telericacademy.web.deliverit.models.Shipment;
import com.telericacademy.web.deliverit.repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentServiceImpl implements ShipmentService {
    ShipmentRepository shipmentRepository;

    @Autowired
    public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

//
//    @Override
//    public List<Shipment> getAll(Optional<Integer> warehouseId) {
//        return shipmentRepository.getAll(warehouseId);
//    }

    @Override
    public List<Shipment> getAll() {
        return shipmentRepository.getAll();



    }

    @Override
    public List<Shipment> filterByWarehouse(int warehouseId) {
        return shipmentRepository.filterByWarehouse(warehouseId);
    }

    @Override
    public Shipment getById(int id) {
        return shipmentRepository.getById(id);


    }


//
//    @Override
//    public List<Shipment> filter1(Optional<Integer> originWarehouseId,Optional<Integer> destinationWarehouseId) {
//        return shipmentRepository.filter1(originWarehouseId,destinationWarehouseId );
//    }


    @Override
    public void create(Shipment shipment) {
        if (shipment.getArrivalDate() != null && shipment.getDepartureDate() != null) {
            validDates(shipment);
        }
        if (shipment.getArrivalDate() != null && shipment.getDepartureDate() == null) {
            throw new InvalidUserInputException("Shipment can`t have arrival date without departure date!");
        }
    }


    @Override
    public void update(Shipment shipment) {
        validDates(shipment);
        shipmentRepository.update(shipment);
    }

    @Override
    public void delete(int id) {
        shipmentRepository.delete(id);
    }



    private void validDates(Shipment shipment) {
        if (shipment.getArrivalDate() != null && shipment.getDepartureDate() != null) {
            if (shipment.getArrivalDate().compareTo(shipment.getDepartureDate()) <= 0) {
                throw new InvalidUserInputException("Arrival date must be after departure date");
            }
        }
    }
}
