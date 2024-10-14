package com.example.Attyre.Assignment.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "_users")
public class User{
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "user_sequence"
    )
    private int id;

    @NotEmpty(message = "username cannot be empty or null")
    @Length(min = 2, max = 10, message = "username should have more than 1 character and less than 11 characters")
    private String username;

    @NotEmpty(message = "display_name cannot be empty or null")
    @Length(min = 2, max = 10, message = "display_name should have more than 1 character and less than 11 characters")
    private String display_name;

    private String profile_picture_url = null;
    private String bio = null;
    private int followers_count = 0;
    private boolean verified = false;
}
