package com.telericacademy.web.deliverit.repositories.contracts;

import com.telericacademy.web.deliverit.models.Shipment;
import com.telericacademy.web.deliverit.models.Warehouse;

import java.util.List;

public interface WarehouseRepository {
    List<Warehouse> getAll();

    Warehouse getById(int id);

    Warehouse getByAddressId(int id);

    void create(Warehouse warehouse);

    void update(Warehouse warehouse);

   void delete(int id);

}
