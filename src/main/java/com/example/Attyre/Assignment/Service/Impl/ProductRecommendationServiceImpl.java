package com.example.Attyre.Assignment.Service.Impl;

import com.example.Attyre.Assignment.Entity.Product;
import com.example.Attyre.Assignment.Service.ProductRecommendationService;
import com.example.Attyre.Assignment.Service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
public class ProductRecommendationServiceImpl implements ProductRecommendationService {
    private int itemsCount = 0;
    @Autowired
    private ProductService productService;

    @Autowired
    private UserInteractionServiceImpl userInteractionService;

    private static final Logger logger = LoggerFactory.getLogger(ProductRecommendationServiceImpl.class);

    @Override
    @Transactional
    public List<Product> getRecommendationsByUser(Long userID, int page, int size) {
        List<Product> products = new LinkedList<>();
        if(size == 0) return products;

        itemsCount = size;

        List<Product> popularProducts = productService.getPopularProducts(page, 2);
        selectProducts(products, popularProducts);

        List<Product> interactedProducts = userInteractionService.getProductInteractions(userID, page, 1);
        selectProducts(products, popularProducts);

        List<Product> userPreferProducts = productService.getProductsFromUserPreference(userID, page, itemsCount);
        selectProducts(products, popularProducts);

        return products;
    }

    private void selectProducts(List<Product> products, List<Product> retrievedProducts){
        itemsCount -= retrievedProducts.size();
        System.out.println(retrievedProducts.size());
        if(retrievedProducts != null && !retrievedProducts.isEmpty())
            products.addAll(retrievedProducts);
    }
}
