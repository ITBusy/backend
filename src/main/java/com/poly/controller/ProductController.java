package com.poly.controller;

import com.poly.dto.Convert;
import com.poly.dto.ProductDto;
import com.poly.dto.ResponseObject;
import com.poly.entity.ImageProducts;
import com.poly.entity.Product;
import com.poly.service.impl.ImageProductServiceImp;
import com.poly.service.impl.ProductServiceImp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductServiceImp productServiceImp;

    @Autowired
    private ImageProductServiceImp imageProductServiceImp;

    @PostMapping("/")
    public ResponseEntity<ResponseObject> createProduct(@RequestBody ProductDto productDto) {
        ResponseEntity<ResponseObject> message;
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);

        product.setName(Convert.CapitalAllFirstLetter(product.getName()));
        Product newProduct = this.productServiceImp.createProduct(product);
        if (newProduct != null) {
            if (productDto.getImageUrl() != null) {
                for (ImageProducts p : productDto.getImageUrl()) {
                    ImageProducts imageProducts = new ImageProducts();
                    imageProducts.setImageUrl(p.getImageUrl());
                    imageProducts.setProduct(newProduct);
                    this.imageProductServiceImp.createImageProduct(imageProducts);
                }
            }
            message = ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "Create product is success", newProduct)
            );
        } else {
            message = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", "Create product is failed", null)
            );
        }
        return message;
    }

    @GetMapping("/")
    public ResponseEntity<ResponseObject> findAllProduct() {
        return ResponseEntity.ok(new ResponseObject("Ok", "Get data is success", this.productServiceImp.findAllProducts()));
    }

    @PutMapping("/")
    public ResponseEntity<ResponseObject> updateProduct(@RequestBody ProductDto productDto) {
        ResponseEntity<ResponseObject> message;
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        product.setImageMain(this.productServiceImp.findProductById(productDto.getPId()).getImageMain());
        product.setName(Convert.CapitalAllFirstLetter(product.getName()));
        Product newProduct = this.productServiceImp.createProduct(product);
        if (newProduct != null) {
            if (productDto.getImageUrl() != null) {
                Set<String> imageProductsSet = this.imageProductServiceImp.findImageProductsByProduct(newProduct)
                        .stream().map(ImageProducts::getImageUrl).collect(Collectors.toSet());
//                if (!imageProductsSet.isEmpty()) {
                List<ImageProducts> imageProducts = (productDto.getImageUrl().stream().filter(e ->
                        !imageProductsSet.contains(e.getImageUrl())
                ).collect(Collectors.toList()));
                imageProducts.forEach(imageProducts1 -> {
                    imageProducts1.setProduct(newProduct);
                    this.imageProductServiceImp.createImageProduct(imageProducts1);
                });
//                }
            }
            message = ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "Update product is success", newProduct)
            );
        } else {
            message = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", "Update product is failed", null)
            );
        }
        return message;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findProductById(@PathVariable("id") Long id) {
        ProductDto productDto = this.productServiceImp.findProductById(id);
        if (productDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Not Found", "Don't have data returned", null)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Found", "Have return data", productDto)
            );
        }
    }

    @GetMapping("/manufacturer/{id}")
    public ResponseEntity<ResponseObject> findManufacturerById(@PathVariable("id") Long id) {
        List<ProductDto> productDto = this.productServiceImp.findByManufacturer(id);
        if (productDto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Not Found", "Don't have data returned", null)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Found", "Have return data", productDto)
            );
        }
    }
}
