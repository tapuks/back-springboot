package com.example.app.services;

import org.springframework.http.ResponseEntity;

import com.example.app.response.CategoriaResponseRest;

public interface ICategoriaService {

    public ResponseEntity<CategoriaResponseRest> search();

}
