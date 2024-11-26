package com.example.app.services;

import java.lang.StackWalker.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.app.dao.ICategoriaDao;
import com.example.app.dao.IProductDao;
import com.example.app.model.Categoria;
import com.example.app.model.Product;
import com.example.app.response.productResponseRest;

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

}
