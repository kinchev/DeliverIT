package com.telericacademy.web.deliverit.services;

import com.telericacademy.web.deliverit.exceptions.DuplicateEntityException;
import com.telericacademy.web.deliverit.exceptions.EntityNotFoundException;
import com.telericacademy.web.deliverit.exceptions.InvalidUserInputException;
import com.telericacademy.web.deliverit.exceptions.UnauthorizedOperationException;
import com.telericacademy.web.deliverit.models.Parcel;
import com.telericacademy.web.deliverit.models.Shipment;
import com.telericacademy.web.deliverit.models.User;
import com.telericacademy.web.deliverit.repositories.contracts.ShipmentRepository;
import com.telericacademy.web.deliverit.services.contracts.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentServiceImpl implements ShipmentService {
    private static final String MODIFY_USER_ERROR_MESSAGE = "This operation with shipment is allowed to be done only by employees.";

    private final ShipmentRepository shipmentRepository;

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
    public List<Shipment> getAll(User user) {
        if (!user.isEmployee()) {
            throw new UnauthorizedOperationException(MODIFY_USER_ERROR_MESSAGE);
        }

        return shipmentRepository.getAll();


    }

    @Override
    public List<Shipment> filterByWarehouse(int warehouseId) {

        return shipmentRepository.filterByWarehouse(warehouseId);
    }

    @Override
    public Shipment getById(int id, User user) {
        if (!user.isEmployee()) {
            throw new UnauthorizedOperationException(MODIFY_USER_ERROR_MESSAGE);
        }
        return shipmentRepository.getById(id);


    }

    @Override
    public List<Shipment> filterByCustomer(int customerId) {
        return shipmentRepository.filterByCustomer(customerId);
    }


    @Override
    public void create(Shipment shipment, User user) {
        if (!user.isEmployee()) {
            throw new UnauthorizedOperationException(MODIFY_USER_ERROR_MESSAGE);
        }
        if (shipment.getArrivalDate() != null && shipment.getDepartureDate() != null) {
            validDates(shipment);
        }
        if (shipment.getArrivalDate() != null && shipment.getDepartureDate() == null) {
            throw new InvalidUserInputException("Shipment can`t have arrival date without departure date!");
        }
        boolean duplicateExists = true;
        try {
            shipmentRepository.getById(shipment.getId());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("Shipment", "id", String.valueOf(shipment.getId()));

        }
        shipmentRepository.create(shipment);
    }


    @Override
    public void update(Shipment shipment, User user) {
        if (!user.isEmployee()) {
            throw new UnauthorizedOperationException(MODIFY_USER_ERROR_MESSAGE);
        }
        validDates(shipment);
        shipmentRepository.update(shipment);
    }

    @Override
    public void delete(int id, User user) {
        if (!user.isEmployee()) {
            throw new UnauthorizedOperationException(MODIFY_USER_ERROR_MESSAGE);
        }
        shipmentRepository.delete(id);
    }

    @Override
    public Shipment addParcelToShipment(Shipment shipment, Parcel parcel, User user) {
        if (!user.isEmployee()) {
            throw new UnauthorizedOperationException(MODIFY_USER_ERROR_MESSAGE);
        }
        if(shipment.getOriginWarehouse().getId() != parcel.getOriginWarehouse().getId()) {
            throw new InvalidUserInputException("The Origin Warehouse of Shipment and Parcel must be same");
        }
        if(shipment.getDestinationWarehouse().getId() != parcel.getDestinationWarehouse().getId()) {
            throw new InvalidUserInputException("The Destination Warehouse of Shipment and Parcel must be same");
        }
        var parcels = shipment.getParcels();
        if (parcels.contains(parcel)) {
            throw new DuplicateEntityException(String.format("Parcel with id %d is already included in Shipment with id %d",
                    parcel.getId(), shipment.getId()));
        }
        parcels.add(parcel);
        shipment.setParcels(parcels);
        shipmentRepository.update(shipment);
        return shipment;
    }

    @Override
    public Shipment removeParcelFromShipment(Shipment shipment, Parcel parcel, User user) {
        if (!user.isEmployee()) {
            throw new UnauthorizedOperationException(MODIFY_USER_ERROR_MESSAGE);
        }
        if (shipment == null) {

            throw new EntityNotFoundException("Shipment", shipment.getId());
        }
        if (parcel == null) {
            throw new EntityNotFoundException("Parcel", parcel.getId());
        }
        var parcels = shipment.getParcels();
        if (!parcels.contains(parcel)) {
            throw new EntityNotFoundException(String.format("Parcel with id %d is not included in shipment with id %d",
                    parcel.getId(), shipment.getId()));
        }
        parcels.remove(parcel);
        shipment.setParcels(parcels);
        shipmentRepository.update(shipment);
        return shipment;
    }


    private void validDates(Shipment shipment) {
        if (shipment.getArrivalDate() != null && shipment.getDepartureDate() != null) {
            if (shipment.getArrivalDate().compareTo(shipment.getDepartureDate()) <= 0) {
                throw new InvalidUserInputException("Arrival date must be after departure date");
            }
        }
    }
}
