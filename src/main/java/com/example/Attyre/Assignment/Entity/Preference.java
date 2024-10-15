package com.example.Attyre.Assignment.Entity;

import com.example.Attyre.Assignment.Entity.Enums.Season;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "_user_preference")
public class Preference {
    @Id
    @SequenceGenerator(
            name = "user_preference_sequence",
            sequenceName = "user_preference_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_preference_sequence"
    )
    private Long id;

    private Set<String> category;

    @ElementCollection
    private Set<Season> seasons;

    private Set<String> brands;

    private Set<String> styles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_ID", referencedColumnName = "id")
    private User user;

    public void setCategory(Set<String> userCategory){
        if(this.category == null)
            this.category = new HashSet<>();

        this.category.addAll(userCategory);
    }

    public void setSeasons(Set<Season> userSeason){
        if(seasons == null)
            seasons = new HashSet<>();

        seasons.addAll(userSeason);
    }

    public void setBrands(Set<String> userBrands){
        if(brands == null)
            brands = new HashSet<>();

        brands.addAll(userBrands);
    }

    public void setStyles(Set<String> userStyles){
        if(styles == null)
            styles = new HashSet<>();

        styles.addAll(userStyles);
    }
}
