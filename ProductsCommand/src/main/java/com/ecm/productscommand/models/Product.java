package com.ecm.productscommand.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String price;
    private String description;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    public List<Photo> photos;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime created;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime updated;

    public Product(){
        this.created = LocalDateTime.now();
        this.id = 0;
    }

    public Product(String name, String price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.created = LocalDateTime.now();
    }
}
