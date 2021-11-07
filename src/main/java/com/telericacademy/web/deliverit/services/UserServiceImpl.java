package com.telericacademy.web.deliverit.services;

import com.telericacademy.web.deliverit.exceptions.DuplicateEntityException;
import com.telericacademy.web.deliverit.exceptions.EntityNotFoundException;
import com.telericacademy.web.deliverit.exceptions.UnauthorizedOperationException;
import com.telericacademy.web.deliverit.models.Parcel;
import com.telericacademy.web.deliverit.models.User;
import com.telericacademy.web.deliverit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class UserServiceImpl implements UserService {

    public static final String MODIFY_USER_ERROR_MESSAGE = "Only the user owner or admin can modify an user.";

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> getAll() {
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
    public List<User> search(Optional<String> email, Optional<String> firstName, Optional<String> lastName) {
        return userRepository.search(email, firstName, lastName);
    }

    @Override
    public List<Parcel> getUserParcels(User user) {
        return userRepository.getUserParcels(user);
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
    public List<Parcel> incomingParcels(int id) {
        return userRepository.incomingParcels(id);
    }


}