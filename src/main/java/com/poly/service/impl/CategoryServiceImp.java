package com.poly.service.impl;

import com.poly.entity.Category;
import com.poly.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements com.poly.service.ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public Category findCategoryById(Long id) {
        return this.categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Category> findAllCategories() {
        return this.categoryRepository.findAll();
    }
}
