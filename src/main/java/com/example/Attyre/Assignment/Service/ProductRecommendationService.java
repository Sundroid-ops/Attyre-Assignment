package com.example.Attyre.Assignment.Service;

import com.example.Attyre.Assignment.Entity.Product;

import java.util.List;

public interface ProductRecommendationService {
    public List<Product> getRecommendationsByUser(Long userID, int page, int size);
}
