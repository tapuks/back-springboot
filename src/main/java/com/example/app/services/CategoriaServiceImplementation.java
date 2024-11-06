package com.example.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.dao.ICategoriaDao;
import com.example.app.model.Categoria;
import com.example.app.response.CategoriaResponseRest;

@Service
public class CategoriaServiceImplementation implements ICategoriaService {
    @Autowired // Instancia el objeto
    private ICategoriaDao categoriaDao;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoriaResponseRest> search() {
        CategoriaResponseRest response = new CategoriaResponseRest();

        try {
            List<Categoria> categorias = (List<Categoria>) categoriaDao.findAll();
            response.getCategoriaResponse().setCategorias(categorias);
            response.setMetadata("Respuesta OK", "200", "Respuesta exitosa");
        } catch (Exception e) {
            response.setMetadata("Respuesta ERROR", "500", "Error al consultar categorias");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
