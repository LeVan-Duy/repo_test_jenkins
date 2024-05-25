package com.example.demo.dto;

import com.example.demo.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private UUID id;

    private String name;

    private float price;

    private int quantity;

    private UUID categoryId;

    //        filter
    private float priceMax;

    private float priceMin;

    private int quantityMin;

    private int quantityMax;

    private String orderBy;

    private String sortBy;

    private String categoryName;

    // pagination

    private int page;

    private int size;

}
