package com.example.app.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.app.model.Categoria;

public interface ICategoriaDao extends CrudRepository<Categoria, Long> {

}
