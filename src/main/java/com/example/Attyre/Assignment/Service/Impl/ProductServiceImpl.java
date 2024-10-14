package com.example.Attyre.Assignment.Service.Impl;

import com.example.Attyre.Assignment.DTO.ProductDTO;
import com.example.Attyre.Assignment.Entity.Product;
import com.example.Attyre.Assignment.Exception.ProductNotFoundException;
import com.example.Attyre.Assignment.Repository.ProductRepo;
import com.example.Attyre.Assignment.Service.ProductService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    @Transactional
    public Product saveProduct(ProductDTO productDTO){
        logger.info("Creating Product");
        Product product = Product.builder()
                .name(productDTO.getName())
                .genderClothing(productDTO.getGenderClothing())
                .category(productDTO.getCategory())
                .brand(productDTO.getBrand())
                .style(productDTO.getStyle())
                .stock(productDTO.getStock())
                .build();

        product.setSeason(productDTO.getSeason());
        Product newProduct = productRepo.save(product);
        logger.info("Product created successfully");
        return newProduct;
    }

    @Override
    public Product getProductByID(Long ID) {
        logger.info("Searching product by id: {}", ID);
        Optional<Product> productOptional = productRepo.findById(ID);

        if(productOptional.isEmpty())
            throw new ProductNotFoundException("Product Not Found for ID: "+ ID);

        Product product = productOptional.get();
        logger.info("Product Found for ID: {}", product.getId());
        return product;
    }
}
