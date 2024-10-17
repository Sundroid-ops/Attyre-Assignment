package com.example.Attyre.Assignment.Service.Impl;

import com.example.Attyre.Assignment.Cache.Service.PopularProductsService;
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
    private PopularProductsService popularProductsService;

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
    @Transactional
    public Product getProductByID(Long productID) {
        Product product = null;
        logger.info("Searching product in DB by id: {}", productID);
        Optional<Product> productOptional = productRepo.findById(productID);

        if(productOptional.isEmpty())
            throw new ProductNotFoundException("Product Not Found for ID: "+ productID);

        product = productOptional.get();
        logger.info("Product Found for ID: {}", product.getId());
        return product;
    }

    @Override
    @Transactional
    public List<Product> getProductsFromUserPreference(Long userID, int page, int size) {
        List<Product> products = new LinkedList<>();
        if (size == 0) return products;

        Preference userPreference = preferenceService.getPreferenceDataByUserID(userID);
        if(userPreference != null) {
            products.addAll(productRepo
                    .getProductsByUserPreference(userPreference.getCategory(), userPreference.getBrands(),
                            userPreference.getSeasons(), userPreference.getStyles(), PageRequest.of(page,size)));
        }
        return products;
    }

    @Override
    @Transactional
    public List<Product> getPopularProducts(int page, int size) {
        if(size == 0) return new LinkedList<>();
        List<Product> products = popularProductsService.getPopularProducts(0, 5);

        if(products == null || products.isEmpty() || products.size() < size){
            logger.info("DB search for popular products");
            int itemSize = size;
            int itemPage = page;
            if(products != null && !products.isEmpty()) {
                itemPage++;
                itemSize = size - products.size();
            }

            List<Product> dbProducts = productRepo.getPopularProducts(PageRequest.of(itemPage, itemSize, Sort.by("rating").descending()));

            if ((dbProducts == null || dbProducts.isEmpty()) && (products == null || products.isEmpty()))
                return products;

            products.addAll(dbProducts);
            popularProductsService.savePopularProducts(products);
        }

        int start = page * size;
        int end = start + size - 1;
        List<Product> popularProducts = new LinkedList<>();

        for(int i=start; i<=end; i++){
            if(products.size() <= i) break;
            popularProducts.add(products.get(i));
        }

        return popularProducts;
    }
}
