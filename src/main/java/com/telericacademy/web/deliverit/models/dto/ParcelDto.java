package com.telericacademy.web.deliverit.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ParcelDto {

    @Positive(message = "User id should be positive.")
    private int userId;

    @Positive(message = "Warehouse id should be positive.")
    private int originWarehouseId;

    @Positive(message = "Warehouse id should be positive.")
    private int destinationWarehouseId;

    @Positive(message = "Weight should be positive.")
    private double weight;

    @Positive(message = "Category id should be positive.")
    private int categoryId;

    private boolean deliverToUser;

    public ParcelDto() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOriginWarehouseId() {
        return originWarehouseId;
    }

    public void setOriginWarehouseId(int originWarehouseId) {
        this.originWarehouseId = originWarehouseId;
    }

    public int getDestinationWarehouseId() {
        return destinationWarehouseId;
    }

    public void setDestinationWarehouseId(int destinationWarehouseId) {
        this.destinationWarehouseId = destinationWarehouseId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isDeliverToUser() {
        return deliverToUser;
    }

    public void setDeliverToUser(boolean deliverToUser) {
        this.deliverToUser = deliverToUser;
    }
}
