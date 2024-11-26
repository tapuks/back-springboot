package com.example.app.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private int cantidad;

    @ManyToOne(fetch = FetchType.LAZY) // Muchos productos pueden pertencer a una categoria. Esta linea es para que no
                                       // se cargue la categoria hasta que se llame
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" }) // Ignora los atributos que no se necesitan
    private Categoria categoria;

    @Lob
    @Basic(fetch = FetchType.LAZY) // Para que la foto se cargue cuando se necesite
    @Column(name = "photo", columnDefinition = "longblob") // Significa que la foto puede tener
    // un tamaño de 1000 bytes
    private byte[] photo;

}
