package com.example.app.services;

import org.springframework.http.ResponseEntity;

import com.example.app.model.Product;
import com.example.app.response.productResponseRest;

public interface IProductService {
    public ResponseEntity<productResponseRest> save(Product product, Long categoriaId);

    public ResponseEntity<productResponseRest> searchById(Long id);

    public ResponseEntity<productResponseRest> searchByName(String name);

}
