package com.example.app.services;

import org.springframework.http.ResponseEntity;

import com.example.app.model.Categoria;
import com.example.app.response.CategoriaResponseRest;

public interface ICategoriaService {

    public ResponseEntity<CategoriaResponseRest> search();

    public ResponseEntity<CategoriaResponseRest> searchById(Long id);

    public ResponseEntity<CategoriaResponseRest> create(Categoria categoria);

    public ResponseEntity<CategoriaResponseRest> update(Categoria categoria, Long id);

    public ResponseEntity<CategoriaResponseRest> delete(Long id);

}
