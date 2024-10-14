package com.example.Attyre.Assignment.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UserDTO {
        @NotEmpty(message = "username cannot be empty or null")
        @Length(min = 2, max = 10, message = "username should have more than 1 character and less than 11 characters")
        private String username;

        @Length(min = 2, max = 10, message = "display_name should have more than 1 character and less than 11 characters")
        private String display_name;

        private String profile_picture_url = null;
        private String bio = null;
        private int followers_count = 0;
        private boolean verified = false;
}
