package com.example.Attyre.Assignment.Service;

import com.example.Attyre.Assignment.DTO.PreferenceDTO;

public interface PreferenceService {
    public void savePreferenceByUserID(PreferenceDTO preferenceDTO, Long userID);
}
