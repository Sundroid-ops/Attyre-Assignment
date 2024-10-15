package com.example.Attyre.Assignment.Repository;

import com.example.Attyre.Assignment.Entity.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferenceRepo extends JpaRepository<Preference, Long> {
}
