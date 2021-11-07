package com.telericacademy.web.deliverit.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

public class ShowShipmentDto {
    private int id;
    @Positive(message = "Origin  warehouse id should be positive.")
    private int originWarehouse;

    @Positive(message = "Destination warehouse id should be positive.")
    private int destinationWarehouse;

    @NotNull(message = "Departure date must be valid")
    LocalDate departureDate;

    @NotNull(message = "Arrival date must be valid")
    LocalDate arrivalDate;
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOriginWarehouse() {
        return originWarehouse;
    }

    public void setOriginWarehouse(String message) {
        this.originWarehouse = originWarehouse;
    }

    public int getDestinationWarehouse() {
        return destinationWarehouse;
    }

    public void setDestinationWarehouse(String message) {
        this.destinationWarehouse = destinationWarehouse;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String message) {
        this.departureDate = departureDate;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String message) {
        this.arrivalDate = arrivalDate;
    }
}
