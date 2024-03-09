package com.ecm.productsquery.services;

import com.ecm.productsquery.exceptions.CustomException;
import com.ecm.productsquery.repositories.ProductRepository;
import com.ecm.productsquery.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service @RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product read(Integer id) {
        Optional<Product> existingProductOptional = productRepository.findById(id);
        if (existingProductOptional.isEmpty()){
            throw new CustomException("Product was not found.", HttpStatus.NOT_FOUND);
        }
        return existingProductOptional.get();
    }

    @Override
    public Product update(Integer id, Product product) {
        Optional<Product> existingProductOptional = productRepository.findById(id);
        if (existingProductOptional.isEmpty()){
            throw new CustomException("Product was not found.", HttpStatus.NOT_FOUND);
        }
        Product existingProduct = existingProductOptional.get();
        existingProduct.setDescription(product.getDescription());
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        return productRepository.save(existingProduct);
    }

    @Override
    public void delete(Integer id) {
        Optional<Product> existingProductOptional = productRepository.findById(id);
        if (existingProductOptional.isEmpty()){
            throw new CustomException("Product was not found.", HttpStatus.NOT_FOUND);
        }
        productRepository.deleteById(id);
    }

    @Override
    public Iterable<Product> search(String query, Integer itemCount, Integer page) {
        return productRepository.findAll();
    }
}
