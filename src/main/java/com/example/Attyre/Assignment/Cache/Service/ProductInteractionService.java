package com.example.Attyre.Assignment.Cache.Service;

import com.example.Attyre.Assignment.Entity.Product;

import java.util.List;

public interface ProductInteractionService {
    public void saveRecentProduct(Long userID, Product product, int TTL);

    public void saveUserInteractedProductIDs(Long userID, Long productID, int TTL);

    public Product getProductByID(Long productID);

    public List<Product> getInteractedProductsByUserID(Long userID);

    public void cacheProductInteraction(Long userID, Product product);
}
