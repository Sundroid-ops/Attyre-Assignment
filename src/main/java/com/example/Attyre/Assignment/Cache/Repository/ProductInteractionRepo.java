package com.example.Attyre.Assignment.Cache.Repository;

import com.example.Attyre.Assignment.Entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInteractionRepo extends CrudRepository<Product, Long> {
}
