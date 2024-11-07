package com.example.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoriaResponseRest> searchById(Long id) {
        CategoriaResponseRest response = new CategoriaResponseRest();
        List<Categoria> categorias = new ArrayList<>();

        try {
            Optional<Categoria> categoria = categoriaDao.findById(id);
            if (categoria.isPresent()) {
                categorias.add(categoria.get());
                response.getCategoriaResponse().setCategorias(categorias);
                response.setMetadata("Respuesta OK", "200", "Respuesta exitosa");

            } else {
                response.setMetadata("Respuesta ERROR", "404", "Categoria no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.setMetadata("Respuesta ERROR", "500", "Error al consultar categoria por id");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Override
    @Transactional
    public ResponseEntity<CategoriaResponseRest> create(Categoria categoria) {
        CategoriaResponseRest response = new CategoriaResponseRest();
        List<Categoria> categorias = new ArrayList<>();

        try {
            Categoria categoriaSave = categoriaDao.save(categoria);
            if (categoriaSave != null) {
                categorias.add(categoriaSave);
                response.getCategoriaResponse().setCategorias(categorias);
                response.setMetadata("Respuesta OK", "200", "Categoria creada exitosamente");
            } else {
                response.setMetadata("Respuesta ERROR", "400", "No se pudo crear la categoria");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.setMetadata("Respuesta ERROR", "500", "Error al crear categoria");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Override
    @Transactional
    public ResponseEntity<CategoriaResponseRest> update(Categoria categoria, Long id) {
        CategoriaResponseRest response = new CategoriaResponseRest();
        List<Categoria> categorias = new ArrayList<>();

        try {
            Optional<Categoria> categoriaSearch = categoriaDao.findById(id);
            if (categoriaSearch.isPresent()) {
                Categoria categoriaUpdate = categoriaSearch.get();
                categoriaUpdate.setName(categoria.getName());
                categoriaUpdate.setDescription(categoria.getDescription());
                Categoria categoriaSave = categoriaDao.save(categoriaUpdate);
                if (categoriaSave != null) {
                    categorias.add(categoriaSave);
                    response.getCategoriaResponse().setCategorias(categorias);
                    response.setMetadata("Respuesta OK", "200", "Categoria modificada exitosamente");
                } else {
                    response.setMetadata("Respuesta ERROR", "400", "No se pudo modificar la categoria");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
            } else {
                response.setMetadata("Respuesta ERROR", "404", "Categoria no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Respuesta ERROR", "500", "Error al modificar categoria");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
