package com.telericacademy.web.deliverit.services;

import com.telericacademy.web.deliverit.models.Shipment;
import com.telericacademy.web.deliverit.models.User;
import com.telericacademy.web.deliverit.models.Warehouse;

import java.util.List;

public interface WarehouseService {
    List<Warehouse> getAll();

    Warehouse getById(int id);

    void create(Warehouse warehouse, User user);

    void update(Warehouse warehouse, User user);

    void delete(int id, User user);



}
