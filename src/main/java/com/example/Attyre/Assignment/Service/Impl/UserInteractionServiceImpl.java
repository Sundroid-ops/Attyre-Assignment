package com.example.Attyre.Assignment.Service.Impl;

import com.example.Attyre.Assignment.Cache.Service.ProductInteractionService;
import com.example.Attyre.Assignment.Entity.Enums.Action;
import com.example.Attyre.Assignment.Entity.Product;
import com.example.Attyre.Assignment.Entity.User;
import com.example.Attyre.Assignment.Entity.UserInteraction;
import com.example.Attyre.Assignment.Repository.UserInteractionRepo;
import com.example.Attyre.Assignment.Service.ProductService;
import com.example.Attyre.Assignment.Service.UserInteractionService;
import com.example.Attyre.Assignment.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class UserInteractionServiceImpl implements UserInteractionService {
    private static final Logger logger = LoggerFactory.getLogger(UserInteractionService.class);

    @Autowired
    private UserInteractionRepo userInteractionRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductInteractionService productInteractionService;

    @Override
    @Transactional
    public void saveInteraction(Long userID, Long productID, Action action) {
        Optional<UserInteraction> getUserInteraction = userInteractionRepo.findByProductAndUserID(userID, productID);
        Product product = null;
        User user = null;
        UserInteraction userInteraction = null;

        if(getUserInteraction.isEmpty()) {
            user = userService.getUserByID(userID);
            if (user == null)
                return;

            product = productService.getProductByID(productID);
            if (product == null)
                return;

            userInteraction = UserInteraction.builder()
                    .user(user)
                    .product(product)
                    .build();

        }else {
            userInteraction = getUserInteraction.get();
            product = userInteraction.getProduct();
        }

        if ((userInteraction.getActions() == null || !userInteraction.getActions().contains(Action.LIKED)) && action == Action.LIKED) {
            product.setRating(product.getRating() + 1);
            logger.info("product ID : {} Liked By Used ID: {}", productID, userID);

        } else if (action == Action.VIEWED) {
            product.setViews(product.getViews() + 1);
            logger.info("product ID : {} Viewed By Used ID: {}", productID, userID);
            userInteraction.setCreatedAt(LocalDateTime.now());
        }

        userInteraction.setActions(action);

        if(productInteractionService.getProductByID(productID) == null)
            productInteractionService.cacheProductInteraction(userID, product);

        userInteractionRepo.save(userInteraction);
    }

    @Override
    @Transactional
    public List<Product> getInteraction(Long userID, int page, int size) {
        List<Product> products = productInteractionService.getInteractedProductsByUserID(userID);

        if(products == null || products.isEmpty() || products.size() < size){
            logger.info("DB search for user interacted products");
            int itemSize = size;
            int itemPage = page;
            if(products != null && !products.isEmpty()) {
                itemPage++;
                itemSize = size - products.size();
            }

            List<Product> dbProducts = userInteractionRepo.getInteractedProductsByUserID(userID, PageRequest.of(itemPage, itemSize, Sort.by("createdAt").descending()));

            if ((dbProducts == null || dbProducts.isEmpty()) && (products == null || products.isEmpty()))
                return products;

            products.addAll(dbProducts);
            for(Product product: dbProducts)
                productInteractionService.saveRecentProduct(userID, product, 15);
        }

        int start = page * size;
        int end = start + size - 1;
        List<Product> recentProducts = new LinkedList<>();

        for(int i=start; i<=end; i++){
            if(products.size() <= i) break;
            recentProducts.add(products.get(i));
        }

        return recentProducts;
    }
}
