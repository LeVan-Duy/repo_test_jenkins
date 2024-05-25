package com.example.demo.controller;

import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tinasoft/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public List<Product> getAll(ProductRequest request){
        return productService.getAll(request);
    }
    @PostMapping("")
    public Product add(@RequestBody ProductRequest request){
        return productService.add(request);
    }


    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable(value = "id")UUID id){
        return productService.delete(id);
    }
}
