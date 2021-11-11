package com.telericacademy.web.deliverit.repositories;

import com.telericacademy.web.deliverit.exceptions.EntityNotFoundException;
import com.telericacademy.web.deliverit.models.City;
import com.telericacademy.web.deliverit.repositories.contracts.CityRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CityRepositoryImpl implements CityRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public CityRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<City> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("from City", City.class)
                    .list();
        }
    }

    public City getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            City city = session.get(City.class, id);
            if (city == null) {
                throw new EntityNotFoundException("City", id);
            }
            return city;
        }
    }

}
