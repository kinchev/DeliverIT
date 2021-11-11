package com.telericacademy.web.deliverit.repositories;

import com.telericacademy.web.deliverit.exceptions.EntityNotFoundException;
import com.telericacademy.web.deliverit.models.Address;
import com.telericacademy.web.deliverit.repositories.contracts.AddressRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AddressRepositoryImpl implements AddressRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public AddressRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Address getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Address address = session.get(Address.class, id);
            if (address == null) {
                throw new EntityNotFoundException("Address", id);
            }
            return address;
        }
    }

    @Override
    public void create(Address address) {
        try (Session session = sessionFactory.openSession()) {
            session.save(address);
        }
    }

}

