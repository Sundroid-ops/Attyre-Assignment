package com.example.Attyre.Assignment.Cache.Service;

import com.example.Attyre.Assignment.Entity.Product;

public interface ProductInteractionService {
    public void saveRecentProduct(Long userID, Product product);
}
