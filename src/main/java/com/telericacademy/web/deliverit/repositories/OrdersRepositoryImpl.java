package com.telericacademy.web.deliverit.repositories;

import com.telericacademy.web.deliverit.exceptions.EntityNotFoundException;
import com.telericacademy.web.deliverit.models.Orders;
import com.telericacademy.web.deliverit.models.Parcel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Access;
import java.util.List;

@Repository
public class OrdersRepositoryImpl implements OrdersRepository {
    private final SessionFactory sessionFactory;

    @Autowired

    public OrdersRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Orders> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Orders> query = session.createQuery("from Orders", Orders.class);
            return query.list();
        }
    }

    @Override
    public Orders getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Orders order = session.get(Orders.class, id);
            if (order == null) {
                throw new EntityNotFoundException("Order", id);
            }
            return order;
        }
    }

    @Override
    public void create(Orders order) {
        try (Session session = sessionFactory.openSession()) {
            session.save(order);
        }

    }

    @Override
    public void update(Orders order) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(order);
            session.getTransaction().commit();
        }

    }


    @Override
    public void delete(int id) {
        Orders orderToDelete=getById(id);
        try(Session session=sessionFactory.openSession()){
            session.beginTransaction();
            session.update(orderToDelete);
            session.getTransaction().commit();
        }

    }
}
