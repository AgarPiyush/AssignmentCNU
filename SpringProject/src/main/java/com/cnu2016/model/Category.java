package com.cnu2016.model;

import javax.persistence.*;

/**
 * Created by Piyush on 7/9/16.
 */
@Entity
@Table(name="Category")
public class Category
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int categoryId;
    @Column(columnDefinition = "mediumtext")
    private String categoryDescription;


    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
}
