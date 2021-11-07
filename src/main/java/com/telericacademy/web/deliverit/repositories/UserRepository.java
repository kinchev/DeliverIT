package com.telericacademy.web.deliverit.repositories;

import com.telericacademy.web.deliverit.models.Parcel;
import com.telericacademy.web.deliverit.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> getAll();

    User getById(int id);

    User getByEmail(String email);

    List<User> search (Optional<String> email, Optional<String> firstName, Optional<String>lastName);

    List<Parcel> getUserParcels(User user);

    void create(User user);

    void update(User user);

    void delete(int id);

}
