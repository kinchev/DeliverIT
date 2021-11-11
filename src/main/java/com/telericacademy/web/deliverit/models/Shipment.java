package com.telericacademy.web.deliverit.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
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
    private Warehouse originWarehouse;

    @ManyToOne
    @JoinColumn(name = "destination_warehouse_id")
    private Warehouse destinationWarehouse;

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

    public Warehouse getOriginWarehouse() {
        return originWarehouse;
    }

    public void setOriginWarehouse(Warehouse originWarehouse) {
        this.originWarehouse = originWarehouse;
    }

    public Warehouse getDestinationWarehouse() {
        return destinationWarehouse;
    }

    public void setDestinationWarehouse(Warehouse destinationWarehouse) {
        this.destinationWarehouse = destinationWarehouse;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shipment shipment = (Shipment) o;
        return getId() == shipment.getId() &&
                Objects.equals(getOriginWarehouse(), shipment.getOriginWarehouse()) &&
                Objects.equals(getDestinationWarehouse(), shipment.getDestinationWarehouse()) &&
                Objects.equals(getArrivalDate(), shipment.getArrivalDate()) &&
                Objects.equals(getDepartureDate(), shipment.getDepartureDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOriginWarehouse(), getDestinationWarehouse(),
                getDepartureDate(), getArrivalDate(), getParcels());
    }
}
