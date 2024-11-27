package com.example.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.dao.ICategoriaDao;
import com.example.app.dao.IProductDao;
import com.example.app.model.Categoria;
import com.example.app.model.Product;
import com.example.app.response.productResponseRest;

import ch.qos.logback.classic.pattern.Util;
import util.util;

@Service
public class ProductServiceImplementation implements IProductService {

    private ICategoriaDao categoriaDao;
    private IProductDao productDao;

    public ProductServiceImplementation(ICategoriaDao categoriaDao, IProductDao productDao) { // Inyeccion de
                                                                                              // dependencias
        super();
        this.categoriaDao = categoriaDao;
        this.productDao = productDao;
    }

    @Override
    @Transactional
    public ResponseEntity<productResponseRest> save(Product product, Long categoriaId) {
        productResponseRest response = new productResponseRest();
        List<Product> products = new ArrayList<>();

        try {
            Optional<Categoria> categoria = this.categoriaDao.findById(categoriaId);
            if (categoria.isPresent()) {
                product.setCategoria(categoria.get());

            } else {
                response.setMetadata("Respuesta ERROR", "404", "Categoria no encontrada");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Product productSave = this.productDao.save(product);
            if (productSave != null) {
                products.add(productSave);
                response.getProductResponse().setProducts(products);
                response.setMetadata("Respuesta OK", "200", "Producto creado con exito");

            } else {
                response.setMetadata("Respuesta ERROR", "400", "Error al crear producto");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.setMetadata("Respuesta ERROR", "500", "Error 500");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<productResponseRest> searchById(Long id) {
        productResponseRest response = new productResponseRest();
        List<Product> products = new ArrayList<>();

        try {
            Optional<Product> product = this.productDao.findById(id);
            if (product.isPresent()) {
                byte[] imageDescompressed = util.decompressZLib(product.get().getPhoto());
                product.get().setPhoto(imageDescompressed);
                products.add(product.get());
                response.getProductResponse().setProducts(products);
                response.setMetadata("Respuesta OK", "200", "Respuesta exitosa");

            } else {
                response.setMetadata("Respuesta ERROR", "404", "Producto no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.setMetadata("Respuesta ERROR", "500", "Error al consultar producto por id");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<productResponseRest> searchByName(String name) {
        productResponseRest response = new productResponseRest();
        List<Product> list = new ArrayList<>();
        List<Product> listAux = new ArrayList<>();

        try {
            listAux = this.productDao.findByNameLike(name);
            if (listAux.size() > 0) {
                listAux.stream().forEach((p) -> {
                    byte[] imageDescompressed = util.decompressZLib(p.getPhoto());
                    p.setPhoto(imageDescompressed);
                    list.add(p);
                });

                response.getProductResponse().setProducts(list);
                response.setMetadata("Respuesta OK", "200", "Productos encontrados");

            } else {
                response.setMetadata("Respuesta ERROR", "404", "Producto no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.setMetadata("Respuesta ERROR", "500", "Error al consultar producto por nombre");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
