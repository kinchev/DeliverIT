package com.telericacademy.web.deliverit.mappers;

import com.telericacademy.web.deliverit.models.*;
import com.telericacademy.web.deliverit.repositories.AddressRepository;
import com.telericacademy.web.deliverit.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class WarehouseMapper {

    private final WarehouseRepository warehouseRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public WarehouseMapper(WarehouseRepository warehouseRepository, AddressRepository addressRepository) {
        this.warehouseRepository = warehouseRepository;
        this.addressRepository = addressRepository;
    }

    public Warehouse fromDto(WarehouseDto warehouseDto) {
        Warehouse warehouse = new Warehouse();
        dtoToObject(warehouseDto, warehouse);
        return warehouse;
    }

    public Warehouse fromDto(WarehouseDto warehouseDto, int id) {
        Warehouse warehouse = warehouseRepository.getById(id);
        dtoToObject(warehouseDto, warehouse);
        return warehouse;
    }

    private void dtoToObject(WarehouseDto warehouseDto, Warehouse warehouse) {
        Address address = addressRepository.getById(warehouseDto.getAddressId());
        warehouse.setAddress(address);
    }

}
