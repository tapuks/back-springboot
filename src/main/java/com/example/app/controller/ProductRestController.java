package com.example.app.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.app.model.Product;
import com.example.app.response.productResponseRest;
import com.example.app.services.IProductService;

import util.util;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/v1")
public class ProductRestController {

    @Autowired
    private IProductService service;

    @PostMapping("/products")
    public ResponseEntity<productResponseRest> saveProduct(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("cantidad") Integer cantidad,
            @RequestParam("categoryId") Long categoryId) throws IOException {

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCantidad(cantidad);
        product.setPhoto(util.compressZLib(photo.getBytes()));

        ResponseEntity<productResponseRest> response = service.save(product, categoryId);

        return response;
    }

}
