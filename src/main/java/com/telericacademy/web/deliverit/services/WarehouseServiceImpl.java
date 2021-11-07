package com.telericacademy.web.deliverit.services;

import com.telericacademy.web.deliverit.exceptions.DuplicateEntityException;
import com.telericacademy.web.deliverit.exceptions.EntityNotFoundException;
import com.telericacademy.web.deliverit.exceptions.UnauthorizedOperationException;
import com.telericacademy.web.deliverit.models.User;
import com.telericacademy.web.deliverit.models.Warehouse;
import com.telericacademy.web.deliverit.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    public static final String MODIFY_WAREHOUSE_ERROR_MESSAGE =
            "Only employees are allowed to make this operation with warehouses";
    private final WarehouseRepository warehouseRepository;

    @Autowired
    public WarehouseServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public List<Warehouse> getAll() {
        return warehouseRepository.getAll();
    }

    @Override
    public Warehouse getById(int id) {
        return warehouseRepository.getById(id);
    }

    @Override
    public void create(Warehouse warehouse, User user) {
        if (!user.isEmployee()) {
            throw new UnauthorizedOperationException(MODIFY_WAREHOUSE_ERROR_MESSAGE);
        }
        boolean duplicateExists = true;
        try {
            warehouseRepository.getByAddressId(warehouse.getAddress().getId());

        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("Warehouse", "Address id", String.valueOf(warehouse.getAddress().getId()));
        }
        warehouseRepository.create(warehouse);
    }

    @Override
    public void update(Warehouse warehouse, User user) {
        if (!user.isEmployee()) {
            throw new UnauthorizedOperationException(MODIFY_WAREHOUSE_ERROR_MESSAGE);
        }
        boolean duplicateExists = true;
        try {
            Warehouse existingWarehouse = warehouseRepository.getByAddressId(warehouse.getAddress().getId());
            if (existingWarehouse.getId() == warehouse.getId()) {
                duplicateExists = false;
            }
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("Warehouse", "Address id", String.valueOf(warehouse.getAddress().getId()));
        }
        warehouseRepository.update(warehouse);
    }

    @Override
    public void delete(int id, User user) {
        if (!user.isEmployee()) {
            throw new UnauthorizedOperationException(MODIFY_WAREHOUSE_ERROR_MESSAGE);
        }
        warehouseRepository.delete(id);
    }
}
