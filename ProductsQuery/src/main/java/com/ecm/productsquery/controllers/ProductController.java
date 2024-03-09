package com.ecm.productsquery.controllers;

import com.ecm.productsquery.models.Product;
import com.ecm.productsquery.services.ProductService;
import com.ecm.productsquery.utilities.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController implements MapperUtil {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> read(@PathVariable("id") String id){
        return ResponseEntity.ok(productService.read(Integer.parseInt(id)));
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

    @GetMapping("/search")
    public ResponseEntity<Iterable<Product>> search(@RequestBody SearchQuery searchQuery){
        return ResponseEntity.ok(productService.search(searchQuery.query, searchQuery.itemCount, searchQuery.page));
    }
    record SearchQuery(String query, Integer itemCount, Integer page){}
}
