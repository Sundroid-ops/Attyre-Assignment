package com.example.Attyre.Assignment.Service;

import com.example.Attyre.Assignment.Entity.Enums.Action;

public interface UserInteractionService {
    public void saveInteraction(Long userID, Long productID, Action action);
}
