package com.example.Attyre.Assignment.Service;

import com.example.Attyre.Assignment.DTO.ProductDTO;
import com.example.Attyre.Assignment.Entity.Product;

import java.util.List;

public interface ProductService {
    public Product saveProduct(ProductDTO productDTO);

    public Product getProductByID(Long ID);

    public List<Product> getProductsFromUserPreference(Long userID, int page, int size);

    public List<Product> getPopularProducts(int page, int size);

}
