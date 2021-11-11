package com.telericacademy.web.deliverit.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

public class ShipmentDto {

    @Positive(message = "Origin  warehouse id should be positive.")
    private int originWarehouseId;

    @Positive(message = "Destination warehouse id should be positive.")
    private int destinationWarehouseId;

//    @NotNull(message = "Departure date must be valid")
    LocalDate departureDate;

//    @NotNull(message = "Departure date must be valid")
    LocalDate arrivalDate;

    public ShipmentDto() {
    }

    public ShipmentDto(int originWarehouseId, int destinationWarehouseId, LocalDate departureDate, LocalDate arrivalDate) {
        this.originWarehouseId = originWarehouseId;
        this.destinationWarehouseId = destinationWarehouseId;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
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

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}
