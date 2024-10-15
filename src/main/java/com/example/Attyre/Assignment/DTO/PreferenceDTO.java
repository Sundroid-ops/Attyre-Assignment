package com.example.Attyre.Assignment.DTO;

import com.example.Attyre.Assignment.Entity.Enums.Season;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PreferenceDTO {
    private Set<String> category;

    private Set<Season> seasons;

    private Set<String> brands;

    private Set<String> styles;
}
