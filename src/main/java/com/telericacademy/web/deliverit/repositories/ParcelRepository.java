package com.telericacademy.web.deliverit.repositories;

import com.telericacademy.web.deliverit.models.Parcel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ParcelRepository {

    List<Parcel> getAll();

    List<Parcel> filter(Optional<Double> minWeight, Optional<String> lastName, Optional<Integer> originWarehouseId,
                        Optional<Integer> destinationWarehouseId, Optional<Integer> categoryId, Optional<String> sort);


    Parcel getById(int id);

    void create(Parcel parcel);

    void update(Parcel parcel);

    void delete(int id);

    List<Parcel> sortBy(Optional<Double> weight, Optional<LocalDate> arrivalDate);
}
