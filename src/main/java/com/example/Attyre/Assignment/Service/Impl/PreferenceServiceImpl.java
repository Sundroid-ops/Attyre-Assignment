package com.example.Attyre.Assignment.Service.Impl;

import com.example.Attyre.Assignment.DTO.PreferenceDTO;
import com.example.Attyre.Assignment.Entity.Preference;
import com.example.Attyre.Assignment.Entity.User;
import com.example.Attyre.Assignment.Exception.UserPreferenceNotRegisteredException;
import com.example.Attyre.Assignment.Repository.PreferenceRepo;
import com.example.Attyre.Assignment.Service.PreferenceService;
import com.example.Attyre.Assignment.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreferenceServiceImpl implements PreferenceService {
    @Autowired
    private PreferenceRepo preferenceRepo;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(PreferenceServiceImpl.class);

    @Override
    public void savePreferenceByUserID(PreferenceDTO preferenceDTO, Long userID) {
        if(preferenceDTO.getStyles() == null && preferenceDTO.getSeasons() == null && preferenceDTO.getBrands() == null && preferenceDTO.getCategory() == null)
            throw new UserPreferenceNotRegisteredException(String.valueOf(userID));

        logger.info("Saving preference of userID : {}", userID);
        User user = userService.getUserByID(userID);

        Preference userPreference = Preference.builder()
                .brands(preferenceDTO.getBrands())
                .category(preferenceDTO.getCategory())
                .styles(preferenceDTO.getStyles())
                .seasons(preferenceDTO.getSeasons())
                .user(user)
                .build();

        preferenceRepo.save(userPreference);
        logger.info("Preference of UserID : {} stored successfully", userID);
    }
}
