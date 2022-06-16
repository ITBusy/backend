package com.poly.service;

import com.poly.entity.ImageProducts;
import com.poly.entity.Product;

import java.util.List;

public interface IImageProductService {
    void createImageProduct(ImageProducts imageProducts);

    List<ImageProducts> findImageProductsByProduct(Product product);

    void deleteImageProductById(Long id);

    ImageProducts findByIdImageProducts(Long id);
}
