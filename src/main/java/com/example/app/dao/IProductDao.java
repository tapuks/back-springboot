package com.example.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.app.model.Product;

public interface IProductDao extends CrudRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.name LIKE CONCAT('%', ?1, '%')")
    List<Product> findByNameLike(String name);

    List<Product> findByNameContaining(String name);

}
