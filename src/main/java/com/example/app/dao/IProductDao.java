package com.example.app.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.app.model.Product;

public interface IProductDao extends CrudRepository<Product, Long> {

}
