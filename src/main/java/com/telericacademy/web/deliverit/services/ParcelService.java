package com.telericacademy.web.deliverit.services;

import com.telericacademy.web.deliverit.models.Parcel;
import com.telericacademy.web.deliverit.models.User;

import java.util.List;
import java.util.Optional;

public interface ParcelService {

    List<Parcel> getAll(User user);

    List<Parcel> filter(User user, Optional<Double> minWeight, Optional<String> lastName, Optional<Integer> originWarehouseId,
                        Optional<Integer> destinationWarehouseId, Optional<Integer> categoryId, Optional<String> sort);


    Parcel getById(int id, User user);

    void create(Parcel parcel, User user);

    void update(Parcel parcel, User user);

    void delete(int id, User user);
}
