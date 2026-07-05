package com.csci44092.product.service;

import com.csci44092.product.entity.Product;
import com.csci44092.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for Product business logic.
 * Follows Single Responsibility Principle — handles only product operations.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Creates and saves a new product.
     *
     * @param product the product to save
     * @return the saved product with generated ID
     */
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the product ID
     * @return the found product
     * @throws RuntimeException if product not found
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
    }

    /**
     * Retrieves all products.
     *
     * @return list of all products
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the product ID to delete
     * @throws RuntimeException if product not found
     */
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }
}
