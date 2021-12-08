package com.backend.service;

import com.backend.model.Adventure;
import com.backend.repository.IAdventureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class AdventureService {

    @Autowired
    IAdventureRepository adventureRepository;

    public Adventure getById(Integer id) {
        Adventure adventure = adventureRepository.fetchById(id);
        adventure.setUnavailablePeriods(new HashSet<>());
        return adventure;
    }

    public List<Adventure> getAllAdventuresFromInstructor(String email) {
        return adventureRepository.getAdventuresByFishingInstructor_Email(email);
    }
}
