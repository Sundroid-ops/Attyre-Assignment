package com.example.Attyre.Assignment.Repository;

import com.example.Attyre.Assignment.Entity.UserInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInteractionRepo extends JpaRepository<UserInteraction, Long> {
    @Query("SELECT p FROM UserInteraction p WHERE p.user.id = ?1 AND p.product.id = ?2")
    Optional<UserInteraction> findByProductAndUserID(Long userID, Long productID);
}
