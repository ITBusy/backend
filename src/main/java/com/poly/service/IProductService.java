package com.poly.service;

import com.poly.dto.ProductDto;
import com.poly.entity.Product;

import java.util.List;

public interface IProductService {
    Product createProduct(Product product);

    List<ProductDto> findAllProducts();

    ProductDto findProductById(Long id);

    List<ProductDto> findByCategories(Long categoryId);

    List<ProductDto> findByManufacturer(Long manufacturerId);
}
