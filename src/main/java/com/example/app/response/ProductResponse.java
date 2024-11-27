package com.example.app.response;

import java.util.List;

import com.example.app.model.Product;

import lombok.Data;

@Data
public class ProductResponse {
    List<Product> products;

}
