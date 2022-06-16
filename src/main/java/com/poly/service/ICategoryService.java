package com.poly.service;

import com.poly.entity.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(Category category);

    Category findCategoryById(Long id);

    List<Category> findAllCategories();
}
