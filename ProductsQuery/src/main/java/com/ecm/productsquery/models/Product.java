package com.ecm.productsquery.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "product", createIndex = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    @Id
    private Integer id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Text)
    private String description;
}