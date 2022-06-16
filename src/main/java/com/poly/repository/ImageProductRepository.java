package com.poly.repository;

import com.poly.entity.ImageProducts;
import com.poly.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageProductRepository extends JpaRepository<ImageProducts, Long> {
    Optional<List<ImageProducts>> findByProduct(Product product);
}
