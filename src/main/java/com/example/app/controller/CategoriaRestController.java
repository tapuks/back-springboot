package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.model.Categoria;
import com.example.app.response.CategoriaResponseRest;
import com.example.app.services.ICategoriaService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

}
