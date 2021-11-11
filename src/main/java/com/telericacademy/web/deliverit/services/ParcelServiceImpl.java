package com.telericacademy.web.deliverit.services;

import com.telericacademy.web.deliverit.exceptions.DuplicateEntityException;
import com.telericacademy.web.deliverit.exceptions.EntityNotFoundException;
import com.telericacademy.web.deliverit.exceptions.UnauthorizedOperationException;
import com.telericacademy.web.deliverit.models.Parcel;
import com.telericacademy.web.deliverit.models.User;
import com.telericacademy.web.deliverit.repositories.contracts.ParcelRepository;
import com.telericacademy.web.deliverit.services.contracts.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParcelServiceImpl implements ParcelService {

    private static final String MODIFY_USER_ERROR_MESSAGE = "This operation with parcels is allowed to be done only by employees.";
    private final ParcelRepository parcelRepository;

    @Autowired
    public ParcelServiceImpl(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    @Override
    public List<Parcel> getAll(User user) {
        if (!user.isEmployee()) {
            throw new UnauthorizedOperationException(MODIFY_USER_ERROR_MESSAGE);
        }
        return parcelRepository.getAll();
    }

    @Override
    public List<Parcel> filter(User user, Optional<Double> minWeight, Optional<String> lastName, Optional<Integer> originWarehouseId,
                               Optional<Integer> destinationWarehouseId, Optional<Integer> categoryId, Optional<String> sort) {
        if (!user.isEmployee()) {
            throw new UnauthorizedOperationException(MODIFY_USER_ERROR_MESSAGE);
        }
        return parcelRepository.filter(minWeight, lastName, originWarehouseId, destinationWarehouseId, categoryId, sort);
    }

    @Override
    public Parcel getById(int id, User user) {
        if (!user.isEmployee()) {
            throw new UnauthorizedOperationException(MODIFY_USER_ERROR_MESSAGE);
        }
        return parcelRepository.getById(id);
    }

    @Override
    public void create(Parcel parcel, User user) {
        if (!user.isEmployee()) {
            throw new UnauthorizedOperationException(MODIFY_USER_ERROR_MESSAGE);
        }
        boolean duplicateExists = true;
        try {
            parcelRepository.getById(parcel.getId());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (duplicateExists) {
            throw new DuplicateEntityException("Parcel", "id", String.valueOf(parcel.getId()));
        }

        parcelRepository.create(parcel);
    }


    @Override
    public void update(Parcel parcel, User user) {
        if (!user.isEmployee()) {
            throw new UnauthorizedOperationException(MODIFY_USER_ERROR_MESSAGE);
        }
        parcelRepository.update(parcel);
    }

    @Override
    public void delete(int id, User user) {
        if (!user.isEmployee()) {
            throw new UnauthorizedOperationException(MODIFY_USER_ERROR_MESSAGE);
        }
        parcelRepository.delete(id);
    }
}
