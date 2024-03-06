package com.ecm.productscommand.services;

import com.ecm.productscommand.models.Product;

public interface ProductService {
    public Product create(Product product);
    public Product update(Integer id, Product product);
    public void delete(Integer id);
}
