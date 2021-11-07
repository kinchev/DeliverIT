package com.telericacademy.web.deliverit.repositories;

import com.telericacademy.web.deliverit.models.Role;

import java.util.List;

public interface RoleRepository {
    List<Role> getAll();

    Role getById(int id);

}
