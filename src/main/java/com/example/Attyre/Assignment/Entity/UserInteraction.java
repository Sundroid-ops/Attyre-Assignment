package com.example.Attyre.Assignment.Entity;


import com.example.Attyre.Assignment.Entity.Enums.Action;
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
@Table(name = "_user_interaction")
public class UserInteraction {
    @Id
    @SequenceGenerator(
            name = "user_interaction_sequence",
            sequenceName = "user_interaction_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_interaction_sequence"
    )
    private Long id;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            optional = false
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            optional = false
    )
    @JoinColumn(
            name = "product_id",
            referencedColumnName = "id"
    )
    private Product product;

    @ElementCollection
    private Set<Action> actions;

    public void setActions(Action action){
        if(actions == null)
            actions = new HashSet<>();

        actions.add(action);
    }
}
