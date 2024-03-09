package com.ecm.productscommand.services;

import com.ecm.productscommand.events.core.MessageQueues;
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
    private final MessageQueues queues;

    public ProductServiceImpl(ProductRepository productRepository, MessageQueues queues) {
        this.productRepository = productRepository;
        this.queues = queues;
    }

    @Override
    public Product create(Product product) {
        Product result = productRepository.save(product);
        queues.products.create.publish(result);
        return result;
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
        Product updatedProduct = productRepository.save(existingProduct);
        queues.products.update.publish(updatedProduct);
        return updatedProduct;
    }

    @Override
    public void delete(Integer id) {
        Optional<Product> existingProductOptional = productRepository.findById(id);
        if (existingProductOptional.isEmpty()){
            throw new CustomException("Product was not found.", HttpStatus.NOT_FOUND);
        }
        productRepository.deleteById(id);
        queues.products.delete.publish(id);
    }
}
