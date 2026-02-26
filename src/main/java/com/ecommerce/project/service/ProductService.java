package com.ecommerce.project.service;

import com.ecommerce.project.dto.ProductRequest;
import com.ecommerce.project.dto.ProductResponse;
import jakarta.validation.Valid;

public interface ProductService {
    ProductRequest addProduct(@Valid ProductRequest productRequest, Long categoryId);

    ProductResponse getAllProduct();

    ProductResponse searchByCategory(Long categoryId);

    ProductResponse searchByKeyword(String keyword);

    ProductRequest updateProduct(@Valid ProductRequest productRequest, Long productId);

    ProductRequest deleteProduct(Long productId);
}
