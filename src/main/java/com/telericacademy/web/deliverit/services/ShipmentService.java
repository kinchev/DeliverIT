package com.telericacademy.web.deliverit.services;

import com.telericacademy.web.deliverit.models.Shipment;
import com.telericacademy.web.deliverit.models.User;

import java.util.List;

public interface ShipmentService {

    List<Shipment> getAll(User user);

    List<Shipment> filterByWarehouse(int warehouseId);

    Shipment getById(int id,User user);

    List<Shipment> filterByCustomer(int customerId);

    void create(Shipment shipment,User user);

    void update(Shipment shipment,User user);

    void delete(int id,User user);


}
