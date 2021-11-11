package com.telericacademy.web.deliverit.models.dto;

import javax.validation.constraints.Positive;

public class WarehouseDto {
    @Positive(message = "Address id should be positive.")
    private int addressId;

    public WarehouseDto() {
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
}
