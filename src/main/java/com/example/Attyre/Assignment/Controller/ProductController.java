package com.example.Attyre.Assignment.Controller;

import com.example.Attyre.Assignment.DTO.ProductDTO;
import com.example.Attyre.Assignment.Entity.Product;
import com.example.Attyre.Assignment.Entity.User;
import com.example.Attyre.Assignment.Service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/")
    public ResponseEntity<Product> saveProduct(@RequestBody @Valid ProductDTO productDTO){
        logger.info("POST request to create new product");
        Product product = productService.saveProduct(productDTO);
        logger.info("Product created successfully");
        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/{productID}")
    public ResponseEntity<Product> getUserByID(@PathVariable Long productID){
        logger.info("GET request for finding product by ID: {}", productID);
        Product product = productService.getProductByID(productID);
        logger.info("Sending user response after finding product by id: {}", productID);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Product>> getPopularProducts(@RequestParam int page, @RequestParam int size){
        logger.info("GET request for getting popular products");
        return ResponseEntity.ok().body(productService.getPopularProducts(page, size));
    }
}
