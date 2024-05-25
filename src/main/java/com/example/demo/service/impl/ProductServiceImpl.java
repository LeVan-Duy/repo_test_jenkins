package com.example.demo.service.impl;

import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.ProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> getAll(ProductRequest request) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        List<Predicate> predicates = new ArrayList<>();

        if (request.getName() != null && !request.getName().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
        }
        if (request.getPriceMin() != 0) {
            predicates.add(criteriaBuilder.gt(root.get("price"), request.getPriceMin()));
        }
        if (request.getPriceMax() != 0) {
            predicates.add(criteriaBuilder.lt(root.get("price"), request.getPriceMax()));
        }
        if (request.getCategoryName() != null && !request.getCategoryName().isEmpty()) {
            Join<Product, Category> categoryJoin = root.join("category");
            predicates.add(criteriaBuilder.like(categoryJoin.get("name"), request.getCategoryName()));
        }

        if (request.getSortBy() != null && request.getOrderBy() != null) {
            if (request.getOrderBy().equalsIgnoreCase("asc")) {
                query.orderBy(criteriaBuilder.asc(root.get(request.getSortBy())));
            } else {
                query.orderBy(criteriaBuilder.desc(root.get(request.getSortBy())));
            }
        }
        query.select(root).where(predicates.toArray(new Predicate[0]));
        TypedQuery<Product> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((request.getPage() - 1) * request.getSize());
        typedQuery.setMaxResults(request.getSize());
        return typedQuery.getResultList();
    }

    @Transactional
    @Override
    public Product add(ProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found!"));
        Product product = new Product();
        product.setCategory(category);
        product.setPrice(request.getPrice());
        product.setName(request.getName());
        product.setQuantity(request.getQuantity());
        entityManager.persist(product);
        return product;


    }

    @Override
    public Product update(ProductRequest request) {
        return null;
    }

    @Override
    public Product findById(String id) {
        return null;
    }

    @Override
    public boolean delete(UUID id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Product> criteriaDelete = criteriaBuilder.createCriteriaDelete(Product.class);
        Root<Product> root = criteriaDelete.from(Product.class);
        criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));
        entityManager.createQuery(criteriaDelete).executeUpdate();
        return true;
    }
}
