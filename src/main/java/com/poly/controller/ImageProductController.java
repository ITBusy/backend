package com.poly.controller;

import com.poly.dto.ResponseObject;
import com.poly.entity.ImageProducts;
import com.poly.service.impl.ImageProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/image-product")
@CrossOrigin("*")
public class ImageProductController {

    @Autowired
    private ImageProductServiceImp imageProductServiceImp;

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteImageProductById(@PathVariable("id") Long id) {
        this.imageProductServiceImp.deleteImageProductById(id);
        return ResponseEntity.ok(new ResponseObject("OK", "Delete with id = " + id + " is success", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findImageProductById(@PathVariable("id") Long id) {
        ImageProducts imageProducts = this.imageProductServiceImp.findByIdImageProducts(id);
        if (imageProducts != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Have return data", imageProducts));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Not Found", "Don't have data returned", null));
        }
    }
}
