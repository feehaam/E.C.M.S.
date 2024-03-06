package com.ecm.productscommand.services;

import com.ecm.productscommand.exceptions.CustomException;
import com.ecm.productscommand.models.Product;
import com.ecm.productscommand.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Integer id, Product product) {
        Optional<Product> existingProductOptional = productRepository.findById(id);
        if (existingProductOptional.isEmpty()){
            throw new CustomException("Product was not found.", HttpStatus.NOT_FOUND);
        }
        Product existingProduct = existingProductOptional.get();
        existingProduct.setDescription(product.getDescription());
        existingProduct.setUpdated(LocalDateTime.now());
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setPhotos(product.getPhotos());

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
}
