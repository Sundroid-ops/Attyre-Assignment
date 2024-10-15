package com.example.Attyre.Assignment.Repository;

import com.example.Attyre.Assignment.Entity.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreferenceRepo extends JpaRepository<Preference, Long> {
    @Query("SELECT p FROM Preference p WHERE p.user.id = ?1")
    Optional<Preference> findByUser(Long userID);
}
