package com.example.Attyre.Assignment.DTO;

import com.example.Attyre.Assignment.Entity.Enums.GenderClothing;
import com.example.Attyre.Assignment.Entity.Enums.Season;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@Getter
@Setter
public class ProductDTO {
        @NotEmpty(message = "name cannot be empty nor null")
        private String name;

        @Enumerated(EnumType.STRING)
        private GenderClothing genderClothing;

        @NotEmpty(message = "clothType cannot be empty nor null")
        private String category;

        @NotEmpty(message = "brand cannot be empty nor null")
        private String brand;

        @Enumerated(EnumType.STRING)
        private Set<Season> season;

        @NotEmpty(message = "style cannot be empty nor null")
        private String style;

        private Long stock;
}
