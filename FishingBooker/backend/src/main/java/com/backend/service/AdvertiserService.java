package com.backend.service;

import com.backend.model.Adventure;
import com.backend.model.Cottage;
import com.backend.model.RegisteredUser;
import com.backend.model.Ship;
import com.backend.repository.IAdventureRepository;
import com.backend.repository.ICottageRepository;
import com.backend.repository.IShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AdvertiserService {

    @Autowired
    private ICottageRepository cottageRepository;

    @Autowired
    private IShipRepository shipRepository;

    @Autowired
    private IAdventureRepository adventureRepository;

    public RegisteredUser findAdvertiserByEntityId(Integer id) {
        Cottage cottage = cottageRepository.fetchOwnerByCottageId(id);
        if (cottage != null) return cottage.getCottageOwner();

        Ship ship = shipRepository.fetchOwnerByShipId(id);
        if (ship != null) return ship.getShipOwner();

        Adventure adventure = adventureRepository.fetchInstructorByAdventureId(id);
        if (adventure != null) return adventure.getFishingInstructor();

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such entity!");
    }
}
