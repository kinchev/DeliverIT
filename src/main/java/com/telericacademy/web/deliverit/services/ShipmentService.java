package com.telericacademy.web.deliverit.services;

import com.telericacademy.web.deliverit.models.Shipment;

import java.util.List;
import java.util.Optional;

public interface ShipmentService {
//    List<Shipment> getAll(Optional<Integer> warehouseId);

    List<Shipment> getAll();
List<Shipment> filterByWarehouse(int warehouseId);
    Shipment getById(int id);


    void create(Shipment shipment);

    void update(Shipment shipment);

    void delete(int id);


}
