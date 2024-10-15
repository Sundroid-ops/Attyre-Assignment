package com.example.Attyre.Assignment.Controller;

import com.example.Attyre.Assignment.DTO.PreferenceDTO;
import com.example.Attyre.Assignment.Entity.Preference;
import com.example.Attyre.Assignment.Service.Impl.PreferenceServiceImpl;
import com.example.Attyre.Assignment.Service.PreferenceService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preference")
public class PreferenceController {
    @Autowired
    private PreferenceService preferenceService;
    private static final Logger logger = LoggerFactory.getLogger(PreferenceServiceImpl.class);

    @PostMapping("/{userID}")
    public ResponseEntity<String> savePreferenceByUserID(@RequestBody @Valid PreferenceDTO preferenceDTO, @PathVariable Long userID){
        logger.info("Incoming POST request to save Preference By UserID for : {}", userID);
        preferenceService.savePreferenceByUserID(preferenceDTO, userID);
        logger.info("Successfully registered the preference of userID: {}", userID);
        return ResponseEntity.ok().body("Preference Registered");
    }

    @GetMapping("/{userID}")
    public ResponseEntity<Preference> getPreferenceByUserID(@PathVariable Long userID){
        logger.info("GET request for finding preference by userID: {}", userID);
        Preference preference = preferenceService.getPreferenceByUserID(userID);
        logger.info("Found user preference for userID : {}", userID);
        return ResponseEntity.ok().body(preference);
    }
}
