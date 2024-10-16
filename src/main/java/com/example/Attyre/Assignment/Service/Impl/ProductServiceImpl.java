package com.example.Attyre.Assignment.Service.Impl;

import com.example.Attyre.Assignment.Cache.Service.ProductInteractionService;
import com.example.Attyre.Assignment.DTO.ProductDTO;
import com.example.Attyre.Assignment.Entity.Enums.Action;
import com.example.Attyre.Assignment.Entity.Preference;
import com.example.Attyre.Assignment.Entity.Product;
import com.example.Attyre.Assignment.Exception.ProductNotFoundException;
import com.example.Attyre.Assignment.Repository.ProductRepo;
import com.example.Attyre.Assignment.Service.PreferenceService;
import com.example.Attyre.Assignment.Service.ProductService;
import com.example.Attyre.Assignment.Service.UserInteractionService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private PreferenceService preferenceService;

    @Autowired
    private ProductInteractionService productInteractionService;

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
    public Product getProductByID(Long productID) {
        Product product = productInteractionService.getProductByID(productID);
        if(product != null)
            return product;

        logger.info("Searching product in DB by id: {}", productID);
        Optional<Product> productOptional = productRepo.findById(productID);

        if(productOptional.isEmpty())
            throw new ProductNotFoundException("Product Not Found for ID: "+ productID);

        product = productOptional.get();
        logger.info("Product Found for ID: {}", product.getId());
        return product;
    }

    @Override
    public List<Product> getProductsFromUserPreference(Long userID, int page, int size) {
        List<Product> products = new LinkedList<>();

        Preference userPreference = preferenceService.getPreferenceDataByUserID(userID);
        if(userPreference != null) {
            products.addAll(productRepo
                    .getProductsByUserPreference(userPreference.getCategory(), userPreference.getBrands(),
                            userPreference.getSeasons(), userPreference.getStyles(), PageRequest.of(page,size)));
        }
        return products;
    }

    @Override
    public List<Product> getPopularProducts(int page, int size) {
        return productRepo.getPopularProducts(PageRequest.of(page, size, Sort.by("rating").descending()));
    }
}
