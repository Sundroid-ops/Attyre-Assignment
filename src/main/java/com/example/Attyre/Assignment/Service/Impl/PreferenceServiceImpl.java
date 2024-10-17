package com.example.Attyre.Assignment.Service.Impl;

import com.example.Attyre.Assignment.DTO.PreferenceDTO;
import com.example.Attyre.Assignment.Entity.Preference;
import com.example.Attyre.Assignment.Entity.User;
import com.example.Attyre.Assignment.Exception.UserPreferenceNotRegisteredException;
import com.example.Attyre.Assignment.Repository.PreferenceRepo;
import com.example.Attyre.Assignment.Repository.ProductRepo;
import com.example.Attyre.Assignment.Service.PreferenceService;
import com.example.Attyre.Assignment.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PreferenceServiceImpl implements PreferenceService {
    @Autowired
    private PreferenceRepo preferenceRepo;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(PreferenceServiceImpl.class);

    @Override
    @Transactional
    public void savePreferenceByUserID(PreferenceDTO preferenceDTO, Long userID) {
        if(preferenceDTO.getStyles() == null && preferenceDTO.getSeasons() == null && preferenceDTO.getBrands() == null && preferenceDTO.getCategory() == null)
            throw new UserPreferenceNotRegisteredException(String.valueOf(userID));

        logger.info("Saving preference of userID : {}", userID);
        User user = userService.getUserByID(userID);

        Preference userPreference = Preference.builder()
                .user(user)
                .build();

        if(preferenceDTO.getStyles() != null)
            userPreference.setStyles(preferenceDTO.getStyles());

        if(preferenceDTO.getBrands() != null)
            userPreference.setBrands(preferenceDTO.getBrands());

        if(preferenceDTO.getCategory() != null)
            userPreference.setCategory(preferenceDTO.getCategory());

        if(preferenceDTO.getSeasons() != null)
            userPreference.setSeasons(preferenceDTO.getSeasons());

        preferenceRepo.save(userPreference);
        logger.info("Preference of UserID : {} stored successfully", userID);
    }

    @Override
    @Transactional
    public Preference getPreferenceDataByUserID(Long userID) {
        logger.info("sending recommendations based on user preference");
        Optional<Preference> userPreference = preferenceRepo.findByUser(userID);

        if(userPreference.isEmpty())
            return null;

        logger.info("user preference found for userID : {}", userPreference);
        return userPreference.get();
    }
}
