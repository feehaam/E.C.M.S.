package com.ecm.productsquery.services;

import com.ecm.productsquery.models.Product;


public interface ProductService {
    Product create(Product product);
    Product read(Integer id);
    Product update(Integer id, Product product0);
    void delete(Integer id);
    Iterable<Product> search(String query, Integer itemCount, Integer page);
}
