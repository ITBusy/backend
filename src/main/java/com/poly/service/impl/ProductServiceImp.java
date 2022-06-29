package com.poly.service.impl;

import com.poly.dto.ProductDto;
import com.poly.entity.Product;
import com.poly.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImp implements com.poly.service.IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageProductServiceImp imageProductServiceImp;

    @Override
    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public List<ProductDto> findAllProducts() {
        List<Product> productList = this.productRepository.findAll();
        return this.getProductDtos(productList);
    }

    private List<ProductDto> getProductDtos(List<Product> productList) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product p : productList) {
            ProductDto productDto = new ProductDto();
            BeanUtils.copyProperties(p, productDto);
            productDto.setImageUrl(this.imageProductServiceImp.findImageProductsByProduct(p));
            productDtos.add(productDto);
        }
        return productDtos;
    }

    @Override
    public ProductDto findProductById(Long id) {
        Product product = this.productRepository.findById(id).orElse(null);
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        return this.getProductDtos(productList).get(0);
    }

    @Override
    public List<ProductDto> findByCategories(Long categoryId) {
        List<Product> product = this.productRepository.findByCategory_cId(categoryId).orElse(null);
        assert product != null;
        return this.getProductDtos(product);
    }

    @Override
    public List<ProductDto> findByManufacturer(Long manufacturerId) {
        List<Product> productList = this.productRepository.findByManufacturer_id(manufacturerId).orElse(null);
        assert productList != null;
        return this.getProductDtos(productList);
    }

    @Override
    public Product updateActiveProduct(Product product) {
        return this.productRepository.save(product);
    }

    //            productDto.setPId(p.getpId());
//            productDto.setName(p.getName());
//            productDto.setActive(p.isActive());
//            productDto.setCategory(p.getCategory());
//            productDto.setCpu(p.getCpu());
//            productDto.setGpu(p.getGpu());
//            productDto.setInsights(p.getInsights());
//            productDto.setManufacturer(p.getManufacturer());
//            productDto.setMonitors(p.getMonitors());
//            productDto.setRamdisk(p.getRamdisk());
//            productDto.setBatteryCapacity(p.getBatteryCapacity());
//            productDto.setWarranty(p.getWarranty());
}
