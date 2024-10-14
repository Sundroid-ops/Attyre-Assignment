package com.example.Attyre.Assignment.Entity;

import com.example.Attyre.Assignment.Entity.Enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

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
    private Long id;

    @NotEmpty(message = "username cannot be empty or null")
    @Column(unique = true)
    @Length(min = 2, max = 10, message = "username should have more than 1 character and less than 11 characters")
    private String username;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    //private List<> preference;

    private LocalDateTime createdAt;
}
