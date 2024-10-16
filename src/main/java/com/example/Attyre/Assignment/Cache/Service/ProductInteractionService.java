package com.example.Attyre.Assignment.Cache.Service;

import com.example.Attyre.Assignment.Entity.Product;

public interface ProductInteractionService {
    public void saveRecentProduct(Product product, int TTL);

    public Product getProductByID(Long productID);

    public void cacheProductInteraction(Product product);
}
