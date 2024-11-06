package com.example.app.response;

import java.util.List;

import com.example.app.model.Categoria;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoriaResponse {
    private List<Categoria> categorias;
}
