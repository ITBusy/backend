package com.poly.repository;

import com.poly.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<List<Product>> findByCategory_cId(Long cId);

    Optional<List<Product>> findByManufacturer_id(Long manufacturerId);


}
