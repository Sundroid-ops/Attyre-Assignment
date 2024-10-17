package com.example.Attyre.Assignment.Repository;

import com.example.Attyre.Assignment.Entity.Enums.GenderClothing;
import com.example.Attyre.Assignment.Entity.Enums.Season;
import com.example.Attyre.Assignment.Entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepoTest {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        Set<Season> seasons1 = new HashSet<>();
        seasons1.add(Season.SUMMER);

        Product product1 = Product.builder()
                .name("Puma t-shirt")
                .style("casual")
                .stock(10L)
                .brand("Puma")
                .genderClothing(GenderClothing.MALE)
                .category("t-shirt")
                .rating(5)
                .views(10)
                .season(seasons1)
                .build();

        entityManager.persist(product1);

        Set<Season> seasons2 = new HashSet<>();
        seasons2.add(Season.SPRING);
        seasons2.add(Season.SUMMER);

        Product product2 = Product.builder()
                .name("Allen solly shirt")
                .style("formal")
                .stock(5L)
                .brand("Allen solly")
                .genderClothing(GenderClothing.FEMALE)
                .category("shirt")
                .rating(3)
                .views(9)
                .season(seasons2)
                .build();

        entityManager.persist(product2);

        Product product3 = Product.builder()
                .name("Polo shirt")
                .style("casual")
                .stock(15L)
                .brand("Polo")
                .genderClothing(GenderClothing.MALE)
                .category("shirt")
                .rating(7)
                .views(15)
                .season(seasons2)
                .build();

        entityManager.persist(product3);
    }

    @Test
    void getProductsByUserPreference() {
        Set<String> categories = new HashSet<>();
        Set<String> brands = new HashSet<>();
        Set<String> styles = new HashSet<>();
        Set<Season> seasons = new HashSet<>();

        categories.add("shirt");
        styles.add("casual");
        seasons.add(Season.SUMMER);

        List<Product> productList = productRepo.getProductsByUserPreference(categories, brands, seasons, styles, PageRequest.of(0, 2));

        assertEquals(productList.size(), 2);
        assertTrue(productList.get(0).getSeason().contains(Season.SUMMER));
        assertEquals(productList.get(1).getStyle(), "casual");
    }

    @Test
    void getPopularProducts() {
        List<Product> products = productRepo.getPopularProducts(PageRequest.of(0, 3));
        assertEquals(products.size(), 3);
        assertTrue(products.get(0).getRating() > products.get(1).getRating());
    }
}