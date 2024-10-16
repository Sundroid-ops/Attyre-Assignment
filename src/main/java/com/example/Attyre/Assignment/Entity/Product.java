package com.example.Attyre.Assignment.Entity;

import com.example.Attyre.Assignment.Entity.Enums.GenderClothing;
import com.example.Attyre.Assignment.Entity.Enums.Season;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "_products")
public class Product implements Serializable {
    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private Long id;

    @NotEmpty(message = "name cannot be empty nor null")
    private String name;

    @Enumerated(EnumType.STRING)
    private GenderClothing genderClothing;

    @NotEmpty(message = "clothType cannot be empty nor null")
    private String category;

    @NotEmpty(message = "brand cannot be empty nor null")
    private String brand;

    @ElementCollection
    private Set<Season> season;

    @NotEmpty(message = "style cannot be empty nor null")
    private String style;

    private Long stock;

    private int rating = 0;

    private int views = 0;

    public void setSeason(Set<Season> seasonList){
        if(season == null)
            season = new HashSet<>();

        season.addAll(seasonList);
    }
}
