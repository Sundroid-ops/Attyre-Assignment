package com.example.Attyre.Assignment.Service;

import com.example.Attyre.Assignment.DTO.ProductDTO;
import com.example.Attyre.Assignment.Entity.Product;

public interface ProductService {
    public Product saveProduct(ProductDTO productDTO);

    public Product getProductByID(Long ID);
}
