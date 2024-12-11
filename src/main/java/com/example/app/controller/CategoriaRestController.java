package com.example.app.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.model.Categoria;
import com.example.app.response.CategoriaResponseRest;
import com.example.app.services.ICategoriaService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import util.CategoryExcelExporter;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/v1")
public class CategoriaRestController {

    @Autowired
    private ICategoriaService service;

    @GetMapping("/categorias")
    public ResponseEntity<CategoriaResponseRest> searchCategorias() {
        ResponseEntity<CategoriaResponseRest> response = service.search();
        return response;

    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<CategoriaResponseRest> searchCategoriasById(@PathVariable Long id) {
        ResponseEntity<CategoriaResponseRest> response = service.searchById(id);
        return response;

    }

    @PostMapping("/categorias")
    public ResponseEntity<CategoriaResponseRest> createCategory(@RequestBody Categoria category) {
        ResponseEntity<CategoriaResponseRest> response = service.create(category);
        return response;

    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<CategoriaResponseRest> updateCategory(@RequestBody Categoria category,
            @PathVariable Long id) {
        ResponseEntity<CategoriaResponseRest> response = service.update(category, id);
        return response;

    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<CategoriaResponseRest> deleteCategory(@PathVariable Long id) {
        ResponseEntity<CategoriaResponseRest> response = service.delete(id);
        return response;

    }

    @GetMapping("/categorias/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerkey = "Content-Disposition";
        String headerValue = "attachment; filename=categories.xlsx";
        response.setHeader(headerkey, headerValue);

        ResponseEntity<CategoriaResponseRest> categoryResponse = service.search();

        CategoryExcelExporter excelExporter = new CategoryExcelExporter(
                categoryResponse.getBody().getCategoriaResponse().getCategorias());

        excelExporter.export(response);
    }

}
