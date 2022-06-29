package com.poly.controller;

import com.poly.dto.ResponseObject;
import com.poly.entity.Category;
import com.poly.service.impl.CategoryServiceImp;
import com.poly.utils.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    private CategoryServiceImp categoryServiceImp;

    @PostMapping("/")
    public ResponseEntity<ResponseObject> createCategory(@RequestBody Category category) {
        ResponseEntity<ResponseObject> message;
        category.setName(Convert.CapitalAllFirstLetter(category.getName()));
        Category category1 = this.categoryServiceImp.createCategory(category);
        if (category1 != null) {
            message = ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "Create category is success", category1)
            );
        } else {
            message = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", "Create category is failed", null)
            );
        }
        return message;
    }

    @GetMapping("/")
    public ResponseEntity<ResponseObject> findAllCategory() {
        List<Category> categoryList = this.categoryServiceImp.findAllCategories();
        return ResponseEntity.ok(new ResponseObject("Ok", "Have data", categoryList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findCategoryById(@PathVariable("id") Long id) {
        Category category = this.categoryServiceImp.findCategoryById(id);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Not Found", "Don't found data with id= " + id, null)
            );
        } else {
            return ResponseEntity.ok(new ResponseObject("Found", "Found data with id= " + id, category));
        }
    }

    @PutMapping("/")
    public ResponseEntity<ResponseObject> updateCategory(@RequestBody Category category) {
        ResponseEntity<ResponseObject> message;
        category.setName(Convert.CapitalFirstLetter(category.getName()));
        Category category1 = this.categoryServiceImp.createCategory(category);
        if (category1 != null) {
            message = ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "Update category is success", category1)
            );
        } else {
            message = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", "Update category is failed", null)
            );
        }
        return message;
    }
}
