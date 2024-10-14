package com.example.Attyre.Assignment.Controller;

import com.example.Attyre.Assignment.DTO.ProductDTO;
import com.example.Attyre.Assignment.Entity.Product;
import com.example.Attyre.Assignment.Service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
