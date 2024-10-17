package com.example.Attyre.Assignment.Cache.Service.Impl;

import com.example.Attyre.Assignment.Cache.Service.ProductInteractionService;
import com.example.Attyre.Assignment.Entity.Product;
import com.example.Attyre.Assignment.Service.Impl.ProductServiceImpl;
import com.example.Attyre.Assignment.Service.ProductService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class ProductInteractionServiceImpl implements ProductInteractionService {
    private static final String RECENTKEY = "RECENT";

    private static final String USERKEY = "USER_INTERACTED_PRODUCT";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductService productService;

    @Override
    public void saveRecentProduct(Long userID, Product product, int TTL) {
        if(getProductByID(product.getId()) != null) return;

        redisTemplate.opsForHash().put(RECENTKEY, product.getId(), product);
        redisTemplate.expire(RECENTKEY, TTL, TimeUnit.MINUTES);
        logger.info("ProductID: {} saved with TTL {}", product.getId(), TTL);

        saveUserInteractedProductIDs(userID, product.getId(), TTL);
    }

    @Override
    public void saveUserInteractedProductIDs(Long userID, Long productID, int TTL) {
        Set<Long> recentProducts = (Set<Long>) redisTemplate.opsForHash().get(USERKEY, userID);
        if(recentProducts != null) {
            if (recentProducts.contains(productID)) return;

        }else
            recentProducts = new HashSet<>();

        recentProducts.add(productID);
        redisTemplate.opsForHash().put(USERKEY, userID, recentProducts);
        redisTemplate.expire(USERKEY, TTL, TimeUnit.MINUTES);
        logger.info("ID for product: {} saved for userID: {}", productID, userID);
    }

    @Override
    public Product getProductByID(Long productID) {
        Product product = (Product) redisTemplate.opsForHash().get(RECENTKEY, productID);
        if(product != null){
            logger.info("Cache HIT for productID: {}", productID);
            return product;
        }

        logger.info("Cache MISS for productID: {}", productID);
        return null;
    }

    @Override
    public List<Product> getInteractedProductsByUserID(Long userID) {
        Set<Long> productIDs =  (Set<Long>) redisTemplate.opsForHash().get(USERKEY, userID);
        List<Product> products = new LinkedList<>();

        if(productIDs == null || productIDs.isEmpty()) {
            logger.info("Cache miss for searching of productIDs");
            return products;
        }
        for(Long ID: productIDs){
            Product product = getProductByID(ID);

            if(product != null)
                products.add(product);
        }

        logger.info("{} products found after retrieving productIDs", products.size());
        return products;
    }

    @Override
    public void cacheProductInteraction(Long userID, Product product){
        // TTL is short as stock is low to prevent stale product data
        if(product.getStock() < 20) {
            saveRecentProduct(userID, product, 10);
            return;

        }
        saveRecentProduct(userID, product, 30);
    }
}
