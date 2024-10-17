package com.example.Attyre.Assignment.Repository;

import com.example.Attyre.Assignment.Entity.Enums.Action;
import com.example.Attyre.Assignment.Entity.Enums.Gender;
import com.example.Attyre.Assignment.Entity.Enums.GenderClothing;
import com.example.Attyre.Assignment.Entity.Enums.Season;
import com.example.Attyre.Assignment.Entity.Product;
import com.example.Attyre.Assignment.Entity.User;
import com.example.Attyre.Assignment.Entity.UserInteraction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserInteractionRepoTest {
    @Autowired
    private UserInteractionRepo userInteractionRepo;

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

        User user = User.builder()
                .username("abc_xyz")
                .gender(Gender.FEMALE)
                .createdAt(LocalDateTime.now())
                .build();

        Set<Action> action1 = new HashSet<>();
        action1.add(Action.LIKED);

        UserInteraction userInteraction1 = UserInteraction.builder()
                .product(product1)
                .user(user)
                .actions(action1)
                .build();

        Set<Action> action2 = new HashSet<>();
        action2.add(Action.LIKED);
        action2.add(Action.VIEWED);

        UserInteraction userInteraction2 = UserInteraction.builder()
                .product(product2)
                .user(user)
                .actions(action2)
                .build();

        entityManager.persist(userInteraction1);
        entityManager.persist(userInteraction2);
    }

    @Test
    void getInteractedProductsByUserID() {
        List<Product> products = userInteractionRepo.getInteractedProductsByUserID(1L, PageRequest.of(0, 2));

        assertEquals(products.size(), 2);
        assertEquals(products.get(0).getSeason().contains(Action.VIEWED), products.get(1).getSeason().contains(Action.VIEWED));
    }
}