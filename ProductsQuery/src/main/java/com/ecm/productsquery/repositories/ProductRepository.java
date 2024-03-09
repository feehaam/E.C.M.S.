package com.ecm.productsquery.repositories;

import com.ecm.productsquery.models.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductRepository extends ElasticsearchRepository<Product, Integer> {
}
