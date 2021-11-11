package com.telericacademy.web.deliverit.services.contracts;

import com.telericacademy.web.deliverit.models.Parcel;
import com.telericacademy.web.deliverit.models.Status;
import com.telericacademy.web.deliverit.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Long countCustomers();

    List<User> getAll();

    List<User> getAllUsers(User user);

    User getById(int id);

    User getByEmail(String email);

    List<User> search(User user, Optional<String> email, Optional<String> firstName, Optional<String> lastName);

    List<Parcel> getUserParcels(User user, User loggedUser);

    List<Parcel> filterUserParcels(User user, User loggedUsed, Optional<String> delivered, Optional<String> preparing,
                                   Optional<String> incoming);

    void create(User user);

    void update(User user, User updatingUser);

    void delete(int id, User updatingUser);

    Status getParcelStatus(User loggedUser, int parcelId);

    Parcel setParcelStatus(User loggedUser, int parcelId, boolean deliverToUser);

}
