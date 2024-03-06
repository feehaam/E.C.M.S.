package com.ecm.productscommand.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity @AllArgsConstructor @Getter @Setter
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String url;
    private String alternative;
    private String details;
    @ManyToOne
    @JoinColumn(name = "photos")
    @JsonIgnore
    private Product product;

    public Photo(){
        id = 0;
    }
}
