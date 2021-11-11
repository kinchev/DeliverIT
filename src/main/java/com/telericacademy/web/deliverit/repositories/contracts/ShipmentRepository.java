package com.telericacademy.web.deliverit.repositories.contracts;

import com.telericacademy.web.deliverit.models.Parcel;
import com.telericacademy.web.deliverit.models.Shipment;
import com.telericacademy.web.deliverit.models.User;

import java.util.List;
import java.util.Optional;

public interface ShipmentRepository {
    List<Shipment> getAll();

    List<Shipment> filterByWarehouse(int warehouseId);

    List<Shipment> filterByCustomer(int customerId);

    Shipment getById(int id);

    Shipment getByParcel(int parcelId);

    void create(Shipment shipment);

    void update(Shipment shipment);

    void delete(int id);

}
