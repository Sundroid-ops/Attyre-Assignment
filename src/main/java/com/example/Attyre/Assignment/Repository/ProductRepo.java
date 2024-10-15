package com.example.Attyre.Assignment.Repository;

import com.example.Attyre.Assignment.Entity.Enums.Season;
import com.example.Attyre.Assignment.Entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p " +
        "WHERE p.category IN ?1 " +
            "AND (p.brand IS NULL OR p.brand IN ?2) " +
            "AND (p.season IS EMPTY OR EXISTS (SELECT s FROM p.season s WHERE s IN ?3)) " +
            "OR (p.style IS NULL OR p.style IN ?4)")
    List<Product> getProductsByUserPreference(Set<String> categories, Set<String> brands, Set<Season> seasons, Set<String> styles, Pageable pageable);

    @Query("SELECT p FROM Product p")
    List<Product> getPopularProducts(Pageable pageable);
}
