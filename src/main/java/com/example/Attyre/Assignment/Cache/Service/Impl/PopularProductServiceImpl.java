package com.example.Attyre.Assignment.Cache.Service.Impl;

import com.example.Attyre.Assignment.Cache.Service.PopularProductsService;
import com.example.Attyre.Assignment.Entity.Product;
import com.example.Attyre.Assignment.Service.Impl.ProductServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class PopularProductServiceImpl implements PopularProductsService {
    private static final String KEY = "POPULAR";
    @Autowired
    private RedisTemplate<String, Object> redisTemplateObject;
    @Autowired
    private RedisTemplate<String, Long> redisTemplateObjectLong;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public void savePopularProducts(List<Product> products) {
        for(Product product: products){
            redisTemplateObject.opsForHash().put(KEY, product.getId(), product);
            redisTemplateObject.expire(KEY, 15, TimeUnit.MINUTES);
        }
        logger.info("Saved popular products");
        saveProductIDsOnRating(products);
    }

    @Override
    public List<Product> getPopularProducts(int start, int end) {
        Set<Long> productIDs = redisTemplateObjectLong.opsForZSet().reverseRange("PRODUCTS_BY_RATING", start, end);

        List<Product> products = new LinkedList<>();
        if (productIDs == null || productIDs.isEmpty()) {
            logger.info("Cache miss for popular productIDs");
            return new LinkedList<>();
        }

        logger.info("Cache hit for popular productIDs");
        for (Long productID: productIDs) {
            Product product = (Product) redisTemplateObject.opsForHash().get(KEY, productID);

            if (product != null) {
                System.out.println("cache " + product.getId());
                products.add(product);
            }
        }

        return products;
    }

    private void saveProductIDsOnRating(List<Product> products){
        for(Product product: products){
            redisTemplateObjectLong.opsForZSet().add("PRODUCTS_BY_RATING", product.getId(), product.getRating());
            redisTemplateObjectLong.expire("PRODUCTS_BY_RATING", 15, TimeUnit.MINUTES);
        }
        logger.info("Cached product IDs with rating");
    }
}
