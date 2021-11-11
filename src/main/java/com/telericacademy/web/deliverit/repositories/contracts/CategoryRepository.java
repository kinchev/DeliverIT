package com.telericacademy.web.deliverit.repositories.contracts;

import com.telericacademy.web.deliverit.models.Category;

public interface CategoryRepository {

    Category getById (int id);
}
