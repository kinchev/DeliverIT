package com.telericacademy.web.deliverit.repositories.contracts;

import com.telericacademy.web.deliverit.models.Orders;
import com.telericacademy.web.deliverit.models.Parcel;

import java.util.List;

public interface OrdersRepository {
    List<Orders> getAll();

    Orders getById(int id);


    void create(Orders order);

    void update(Orders order);

    void delete(int id);

}
