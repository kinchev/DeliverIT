package com.telericacademy.web.deliverit.repositories;

import com.telericacademy.web.deliverit.exceptions.EntityNotFoundException;
import com.telericacademy.web.deliverit.models.Country;
import com.telericacademy.web.deliverit.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountryRepositoryImpl implements CountryRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public CountryRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Country> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("from Country", Country.class)
                    .list();
        }
    }

    @Override
    public Country getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Country country = session.get(Country.class, id);
            if (country == null) {
                throw new EntityNotFoundException("Country", id);
            }
            return country;
        }
    }

}


