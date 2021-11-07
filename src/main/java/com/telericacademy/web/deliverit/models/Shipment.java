package com.telericacademy.web.deliverit.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "shipments")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipment_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "origin_warehouse_id")
    private Warehouse originWarehouseId;

    @ManyToOne
    @JoinColumn(name = "destination_warehouse_id")
    private Warehouse destinationWarehouseId;

    @Column(name = "arrival_date")
    private LocalDate arrivalDate;

    @Column(name = "departure_date")
    private LocalDate departureDate;

    @Transient
    private Status status;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "orders",
            joinColumns = @JoinColumn(name = "shipment_id"),
            inverseJoinColumns = @JoinColumn(name = "parcel_id"))
    private Set<Parcel> parcels;

    public Shipment() {

    }

    public Status getStatus() {
        if (getDepartureDate() != null && getDepartureDate().isAfter(LocalDate.now())) {
            return Status.PREPARING;
        } else if (getArrivalDate() != null && LocalDate.now().isAfter(getArrivalDate())) {
            return Status.COMPLETED;
        } else if (getDepartureDate() == null && getArrivalDate() == null) {
            return Status.PREPARING;
        } else {
            return Status.ON_THE_WAY;
        }
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Set<Parcel> getParcels() {
        return parcels;
    }

    public void setParcels(Set<Parcel> parcels) {
        this.parcels = parcels;
    }

    public Warehouse getOriginWarehouseId() {
        return originWarehouseId;
    }

    public void setOriginWarehouseId(Warehouse originWarehouseId) {
        this.originWarehouseId = originWarehouseId;
    }


    public Warehouse getDestinationWarehouseId() {
        return destinationWarehouseId;
    }

    public void setDestinationWarehouseId(Warehouse destinationWarehouseId) {
        this.destinationWarehouseId = destinationWarehouseId;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }
}
