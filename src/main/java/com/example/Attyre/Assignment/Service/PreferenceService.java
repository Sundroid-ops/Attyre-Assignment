package com.example.Attyre.Assignment.Service;

import com.example.Attyre.Assignment.DTO.PreferenceDTO;
import com.example.Attyre.Assignment.Entity.Preference;
import com.example.Attyre.Assignment.Entity.Product;

import java.util.List;

public interface PreferenceService {
    public void savePreferenceByUserID(PreferenceDTO preferenceDTO, Long userID);

    public Preference getPreferenceByUserID(Long userID);

}
