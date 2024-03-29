package com.telericacademy.web.deliverit.models;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "parcels")
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parcel_id")
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "origin_warehouse_id")
    private Warehouse originWarehouse;

    @OneToOne
    @JoinColumn(name = "destination_warehouse_id")
    private Warehouse destinationWarehouse;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "weight")
    private double weight;

    @Column(name = "deliver_to_user")
    private boolean deliverToUser;

    public Parcel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isDeliverToUser() {
        return deliverToUser;
    }

    public void setDeliverToUser(boolean deliverToUser) {
        this.deliverToUser = deliverToUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parcel parcel = (Parcel) o;
        return getId() == parcel.getId() &&
                Objects.equals(getUser(), parcel.getUser()) &&
                Objects.equals(getOriginWarehouse(), parcel.getOriginWarehouse()) &&
                Objects.equals(getDestinationWarehouse(), parcel.getDestinationWarehouse()) &&
                Objects.equals(getCategory(), parcel.getCategory()) &&
                Double.compare(getWeight(), parcel.getWeight()) == 0 &&
                isDeliverToUser() == parcel.isDeliverToUser();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getOriginWarehouse(), getDestinationWarehouse(),
                getCategory(), getWeight(), isDeliverToUser());
    }

}





