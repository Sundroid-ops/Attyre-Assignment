package com.example.Attyre.Assignment.Service;

import com.example.Attyre.Assignment.Entity.Enums.Action;
import com.example.Attyre.Assignment.Entity.Product;

import java.util.List;

public interface UserInteractionService {
    public void saveInteraction(Long userID, Long productID, Action action);

    public List<Product> getInteraction(Long userID, int page, int size);
}
