package com.csci44092.product.repository;

import com.csci44092.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA Repository for Product entity.
 * Provides CRUD operations out of the box.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
