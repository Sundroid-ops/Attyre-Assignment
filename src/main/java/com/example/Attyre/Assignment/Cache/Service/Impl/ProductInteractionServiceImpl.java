package com.example.Attyre.Assignment.Cache.Service.Impl;

import com.example.Attyre.Assignment.Cache.Service.ProductInteractionService;
import com.example.Attyre.Assignment.Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ProductInteractionServiceImpl implements ProductInteractionService {
    private static final String KEY = "RECENT";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveRecentProduct(Long userID, Product product) {
        redisTemplate.opsForHash().put(KEY, userID, product);
        redisTemplate.expire(KEY, 30, TimeUnit.MINUTES);
    }
}
