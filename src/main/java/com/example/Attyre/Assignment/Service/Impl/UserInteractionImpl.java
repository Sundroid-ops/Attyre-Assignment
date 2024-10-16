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
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInteractionImpl implements UserInteractionService {
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

        }

        userInteraction.setActions(action);

        cacheProductInteraction(product);
        logger.info("cached product ");
        userInteractionRepo.save(userInteraction);
    }

    private void cacheProductInteraction(Product product){
        // TTL is short as stock is low to prevent stale product data
        if(product.getStock() < 20) {
            productInteractionService.saveRecentProduct(product, 10);
            return;

        }
        productInteractionService.saveRecentProduct(product, 30);
    }
}
