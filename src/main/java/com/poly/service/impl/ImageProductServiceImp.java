package com.poly.service.impl;

import com.poly.entity.ImageProducts;
import com.poly.entity.Product;
import com.poly.repository.ImageProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageProductServiceImp implements com.poly.service.IImageProductService {
    @Autowired
    private ImageProductRepository imageProductRepository;

    @Override
    public void createImageProduct(ImageProducts imageProducts) {
        this.imageProductRepository.save(imageProducts);
    }

    @Override
    public List<ImageProducts> findImageProductsByProduct(Product product) {
        return this.imageProductRepository.findByProduct(product).orElse(null);
    }

    @Override
    public void deleteImageProductById(Long id) {
        this.imageProductRepository.deleteById(id);
    }

    @Override
    public ImageProducts findByIdImageProducts(Long id) {
        return this.imageProductRepository.findById(id).orElse(null);
    }
}
