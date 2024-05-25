package com.example.demo.service;

import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<Product> getAll(ProductRequest request);

    Product add(ProductRequest request);

    Product update(ProductRequest request);

    Product findById(String id);

    boolean delete(UUID id);
}
