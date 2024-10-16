package com.example.Attyre.Assignment.Cache.Service.Impl;

import com.example.Attyre.Assignment.Cache.Service.ProductInteractionService;
import com.example.Attyre.Assignment.Entity.Product;
import com.example.Attyre.Assignment.Service.Impl.ProductServiceImpl;
import com.example.Attyre.Assignment.Service.ProductService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ProductInteractionServiceImpl implements ProductInteractionService {
    private static final String KEY = "RECENT";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductService productService;

    @Override
    public void saveRecentProduct(Product product, int TTL) {
        redisTemplate.opsForHash().put(KEY, product.getId(), product);
        redisTemplate.expire(KEY, TTL, TimeUnit.MINUTES);
        logger.info("ProductID: {} saved with TTL", product, TTL);
    }

    @Override
    public Product getProductByID(Long productID) {
        Product product = (Product) redisTemplate.opsForHash().get(KEY, productID);
        if(product != null){
            logger.info("Cache HIT for productID: {}", productID);
            return product;
        }

        logger.info("Cache MISS for productID: {}", productID);
        return null;
    }

    @Override
    public void cacheProductInteraction(Product product){
        // TTL is short as stock is low to prevent stale product data
        if(product.getStock() < 20) {
            saveRecentProduct(product, 10);
            return;

        }
        saveRecentProduct(product, 30);
    }
}
