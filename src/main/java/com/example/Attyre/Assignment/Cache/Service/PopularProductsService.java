package com.example.Attyre.Assignment.Cache.Service;

import com.example.Attyre.Assignment.Entity.Product;

import java.util.List;
import java.util.Set;

public interface PopularProductsService {
    public void savePopularProducts(List<Product> productIDs);

    public List<Product> getPopularProducts(int start, int end);
}
