package com.telericacademy.web.deliverit.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int id;

    @NotBlank
    @Size(min = 2, max = 20, message = "Category name should be with length between 2 and 30 characters.")
    @Column(name = "category_name")
    private String categoryName;

    public Category() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return categoryName;
    }

    public void setName(String name) {
        this.categoryName =categoryName;
    }
}
