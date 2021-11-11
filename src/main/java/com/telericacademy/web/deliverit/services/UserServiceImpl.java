package com.telericacademy.web.deliverit.services;

import com.telericacademy.web.deliverit.exceptions.DuplicateEntityException;
import com.telericacademy.web.deliverit.exceptions.EntityNotFoundException;
import com.telericacademy.web.deliverit.exceptions.UnauthorizedOperationException;
import com.telericacademy.web.deliverit.models.Parcel;
import com.telericacademy.web.deliverit.models.Shipment;
import com.telericacademy.web.deliverit.models.Status;
import com.telericacademy.web.deliverit.models.User;
import com.telericacademy.web.deliverit.repositories.contracts.ParcelRepository;
import com.telericacademy.web.deliverit.repositories.contracts.RoleRepository;
import com.telericacademy.web.deliverit.repositories.contracts.ShipmentRepository;
import com.telericacademy.web.deliverit.repositories.contracts.UserRepository;
import com.telericacademy.web.deliverit.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class UserServiceImpl implements UserService {

    public static final String MODIFY_USER_ERROR_MESSAGE = "Only the user owner or admin can modify an user.";
    public static final String SEARCH_ERROR_MESSAGE = "This operation is allowed only to admins.";
    public static final String LIST_ERROR_MESSAGE = "You have no rights to access other user's parcels unless you are an admin.";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ShipmentRepository shipmentRepository;
    private final ParcelRepository parcelRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ShipmentRepository shipmentRepository, ParcelRepository parcelRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.shipmentRepository = shipmentRepository;
        this.parcelRepository = parcelRepository;
    }

    @Override
    public Long countCustomers() {
        return userRepository.getAll().stream().filter(user -> !user.isEmployee())
                .count();
    }



    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public List<User> getAllUsers(User user) {
        if (!user.isEmployee()) {
            throw new UnauthorizedOperationException(MODIFY_USER_ERROR_MESSAGE);
        }
        return userRepository.getAll();
    }

    @Override
    public User getById(int id) {
        return userRepository.getById(id);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public List<User> search(User user, Optional<String> email, Optional<String> firstName, Optional<String> lastName) {
        if (!user.isEmployee()) {
            throw new UnauthorizedOperationException(SEARCH_ERROR_MESSAGE);
        }
        return userRepository.search(email, firstName, lastName);
    }

    @Override
    public List<Parcel> getUserParcels(User user, User loggedUser) {
        if (!loggedUser.equals(user) && !loggedUser.isEmployee()) {
            throw new UnauthorizedOperationException(LIST_ERROR_MESSAGE);
        }
        return userRepository.getUserParcels(user);
    }

    @Override
    public List<Parcel> filterUserParcels(User user, User loggedUser, Optional<String> delivered,
                                          Optional<String> preparing, Optional<String> incoming) {
        if (!loggedUser.equals(user) && !loggedUser.isEmployee()) {
            throw new UnauthorizedOperationException(LIST_ERROR_MESSAGE);
        }
        return userRepository.filterUserParcels(user, delivered, preparing, incoming);
    }



    @Override
    public void create(User user) {
        boolean duplicateExists = true;
        try {
            userRepository.getByEmail(user.getEmail());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("User", "email", user.getEmail());
        }
        userRepository.create(user);
        var roles = user.getRoles();
        roles.add(roleRepository.getById(1));
        user.setRoles(roles);
        userRepository.update(user);
    }

    @Override
    public void update(User user, User updatingUser) {
        if (!updatingUser.equals(user) && !updatingUser.isEmployee()) {
            throw new UnauthorizedOperationException(MODIFY_USER_ERROR_MESSAGE);
        }
        boolean duplicateExists = true;
        try {
            User existingUser = getByEmail(user.getEmail());
            if (existingUser.getId() == user.getId()) {
                duplicateExists = false;
            }
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("User", "email", user.getEmail());
        }
        userRepository.update(user);
    }

    @Override
    public void delete(int id, User updatingUser) {
        User userToDelete = userRepository.getById(id);
        if (!updatingUser.equals(userToDelete) && !updatingUser.isEmployee()) {
            throw new UnauthorizedOperationException(MODIFY_USER_ERROR_MESSAGE);
        }
        userRepository.delete(id);
    }


    @Override
    public Status getParcelStatus(User loggedUser, int parcelId) {
        try {
            Parcel parcel = parcelRepository.getById(parcelId);
            User user = parcel.getUser();
            if (!loggedUser.equals(user) && !loggedUser.isEmployee()) {
                throw new UnauthorizedOperationException(LIST_ERROR_MESSAGE);
            }
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        }

        Shipment shipment = shipmentRepository.getByParcel(parcelId);
        if (shipment == null) {
            return Status.PREPARING;
        }
        return shipment.getStatus();
    }

    @Override
    public Parcel setParcelStatus(User loggedUser, int parcelId, boolean deliverToUser) {
        try {
            Parcel parcel = parcelRepository.getById(parcelId);
            User user = parcel.getUser();
            if (!loggedUser.equals(user) && !loggedUser.isEmployee()) {
                throw new UnauthorizedOperationException(LIST_ERROR_MESSAGE);
            }
            Shipment shipment = shipmentRepository.getByParcel(parcelId);
            if (!shipment.getStatus().equals(Status.PREPARING))
                throw new UnauthorizedOperationException("The parcel cannot be modified after shipment departure.");
            parcel.setDeliverToUser(deliverToUser);
            parcelRepository.update(parcel);
            return parcel;
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

}

