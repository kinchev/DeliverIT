package com.telericacademy.web.deliverit.repositories;

import com.telericacademy.web.deliverit.exceptions.EntityNotFoundException;
import com.telericacademy.web.deliverit.models.Orders;
import com.telericacademy.web.deliverit.models.Parcel;
import com.telericacademy.web.deliverit.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("from User", User.class)
                    .list();
        }
    }

    @Override
    public User getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            if (user == null) {
                throw new EntityNotFoundException("User", id);
            }
            return user;
        }
    }

    @Override
    public User getByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where email = :email", User.class);
            query.setParameter("email", email);
            List<User> users = query.list();
            if (users.size() == 0) {
                throw new EntityNotFoundException("User", "email", email);
            }
            return users.get(0);
        }
    }

    @Override
    public List<Parcel> incomingParcels(int id) {
        try (Session session = sessionFactory.openSession()) {
            getById(id);
            Query<Orders> query = session.createQuery("from Orders where parcel.user.id=:id and shipment.departureDate<current_date and shipment.arrivalDate>current_date", Orders.class);
            query.setParameter("id", id);
            List<Orders> orders = query.list();
            if (orders.size() == 0) {
                throw new EntityNotFoundException("Parcel", id);
            }
            List<Parcel> parcels = new ArrayList<>();
            for (Orders order : orders) {
                Parcel parcel = session.get(Parcel.class, order.getParcel().getId());

                parcels.add(parcel);
            }
            return parcels;
        }
    }

    @Override
    public List<Parcel> getUserParcels(User user) {
        try (Session session = sessionFactory.openSession()) {
            Query<Parcel> query = session.createQuery("from Parcel where user = :user", Parcel.class);
            query.setParameter("user", user);
            List<Parcel> parcels = query.list();
            if (parcels.size() == 0) {
                throw new IllegalArgumentException(String.format("User with email %s has no parcels", user.getEmail()));
            }
            return parcels;
        }
    }

    @Override
    public List<User> search(Optional<String> email, Optional<String> firstName, Optional<String> lastName) {
        try (Session session = sessionFactory.openSession()) {
            List<User> users = new ArrayList<>();

            email.ifPresent(value -> {
                Query<User> query = session.createQuery("from User where email like :email", User.class);
                query.setParameter("email", "%" + value + "%");
                users.addAll(query.list());
                if (users.size() == 0) {
                    throw new EntityNotFoundException("User", "email (full or part of it)", value);
                }
            });

            firstName.ifPresent(value -> {
                Query<User> query = session.createQuery("from User where firstName = :name", User.class);
                query.setParameter("name", value);
                users.addAll(query.list());
                if (users.size() == 0) {
                    throw new EntityNotFoundException("User", "first name", value);
                }
            });

            lastName.ifPresent(value -> {
                Query<User> query = session.createQuery("from User where lastName = :name", User.class);
                query.setParameter("name", value);
                users.addAll(query.list());
                if (users.size() == 0) {
                    throw new EntityNotFoundException("User", "last name", value);
                }
            });

            return users;
        }
    }

    @Override
    public void create(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.save(user);
        }
    }

    @Override
    public void update(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        User userToDelete = getById(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(userToDelete);
            session.getTransaction().commit();
        }
    }

}

