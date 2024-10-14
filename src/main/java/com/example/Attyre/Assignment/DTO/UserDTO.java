package com.example.Attyre.Assignment.DTO;

import com.example.Attyre.Assignment.Entity.Enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
public class UserDTO {
        @NotEmpty(message = "username cannot be empty or null")
        @Length(min = 2, max = 15, message = "username should have more than 1 character and less than 11 characters")
        private String username;

        @Enumerated(EnumType.STRING)
        private Gender gender;

//        private List<> styles;
//        private List<> brands;
//        private List<> clothes;
//        private int minPrice;
//        private int maxPrice;
}
