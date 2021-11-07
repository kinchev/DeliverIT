package com.telericacademy.web.deliverit.mappers;

import com.telericacademy.web.deliverit.models.*;
import com.telericacademy.web.deliverit.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParcelMapper {

    private ParcelRepository parcelRepository;
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;
    private WarehouseRepository warehouseRepository;

    @Autowired
    public ParcelMapper(ParcelRepository parcelRepository, AddressRepository addressRepository, CategoryRepository categoryRepository,
                        UserRepository userRepository, WarehouseRepository warehouseRepository) {
        this.parcelRepository = parcelRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.warehouseRepository = warehouseRepository;
    }

    public Parcel fromDto(ParcelDto parcelDto) {
        Parcel parcel = new Parcel();
        dtoToObject(parcelDto, parcel);
        return parcel;
    }

    public Parcel fromDto(ParcelDto parcelDto, int id) {
        Parcel parcel = parcelRepository.getById(id);
        dtoToObject(parcelDto, parcel);
        return parcel;
    }

    private void dtoToObject(ParcelDto parcelDto, Parcel parcel) {
        Category category = categoryRepository.getById(parcelDto.getCategoryId());
        User user = userRepository.getById(parcelDto.getUserId());
        Warehouse originWarehouse = warehouseRepository.getById(parcelDto.getOriginWarehouseId());
        Warehouse destinationWarehouse = warehouseRepository.getById(parcelDto.getDestinationWarehouseId());
        parcel.setUser(user);
        parcel.setOriginWarehouse(originWarehouse);
        parcel.setDestinationWarehouse(destinationWarehouse);
        parcel.setCategory(category);
        parcel.setWeight(parcelDto.getWeight());
        parcel.setDeliverToUser(parcelDto.isDeliverToUser());
    }

}
