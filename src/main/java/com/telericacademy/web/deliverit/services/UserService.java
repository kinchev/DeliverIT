package com.telericacademy.web.deliverit.services;

import com.telericacademy.web.deliverit.models.Parcel;
import com.telericacademy.web.deliverit.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAll();

    User getById(int id);

    User getByEmail(String email);

    List<User> search (Optional<String> email, Optional<String> firstName, Optional<String>lastName);

    List<Parcel> getUserParcels(User user);

    void create(User user);

    void update(User user, User updatingUser);

    void delete(int id, User updatingUser);

}
