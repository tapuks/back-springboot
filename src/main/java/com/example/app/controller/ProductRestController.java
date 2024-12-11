package com.example.app.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.app.model.Product;
import com.example.app.response.productResponseRest;
import com.example.app.services.IProductService;

import jakarta.servlet.http.HttpServletResponse;
import util.ProductExcelExporter;
import util.util;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/products/{id}")
    public ResponseEntity<productResponseRest> searchProductById(@PathVariable Long id) {
        ResponseEntity<productResponseRest> response = service.searchById(id);
        return response;
    }

    @GetMapping("/products/filter/{name}")
    public ResponseEntity<productResponseRest> searchProductByName(@PathVariable String name) {
        ResponseEntity<productResponseRest> response = service.searchByName(name);
        return response;
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<productResponseRest> deleteProductById(@PathVariable Long id) {
        ResponseEntity<productResponseRest> response = service.deleteById(id);
        return response;
    }

    @GetMapping("/products")
    public ResponseEntity<productResponseRest> getAllProducts() {
        ResponseEntity<productResponseRest> response = service.getProducts();
        return response;
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<productResponseRest> updateProduct(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("cantidad") Integer cantidad,
            @RequestParam("categoryId") Long categoryId,
            @PathVariable Long id) throws IOException {

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCantidad(cantidad);
        product.setPhoto(util.compressZLib(photo.getBytes()));

        ResponseEntity<productResponseRest> response = service.updateProduct(product, categoryId, id);

        return response;
    }

    @GetMapping("/products/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerkey = "Content-Disposition";
        String headerValue = "attachment; filename=products.xlsx";
        response.setHeader(headerkey, headerValue);

        ResponseEntity<productResponseRest> productResponse = service.getProducts();

        ProductExcelExporter excelExporter = new ProductExcelExporter(
                productResponse.getBody().getProductResponse().getProducts());

        excelExporter.export(response);
    }

}
