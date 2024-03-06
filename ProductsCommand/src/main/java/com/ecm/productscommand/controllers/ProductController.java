package com.ecm.productscommand.controllers;

import com.ecm.productscommand.models.Product;
import com.ecm.productscommand.services.ProductService;
import com.ecm.productscommand.utilities.MapperUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController implements MapperUtil{

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Map<String, Object> productMap){
        Product product = mapToObject(productMap, Product.class);
        return ResponseEntity.ok(productService.create(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable("id") String id, @RequestBody Map<String, Object> productMap){
        Product product = mapToObject(productMap, Product.class);
        return ResponseEntity.ok(productService.update(Integer.parseInt(id), product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id){
        productService.delete(Integer.parseInt(id));
        return ResponseEntity.ok("Product deleted successfully.");
    }
}
