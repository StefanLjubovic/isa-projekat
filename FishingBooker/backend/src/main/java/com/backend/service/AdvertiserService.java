package com.backend.service;

import com.backend.model.*;
import com.backend.repository.IAdventureRepository;
import com.backend.repository.ICottageRepository;
import com.backend.repository.IShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    public List<? extends RentingEntity> findEntitiesByAdvertiserId(Integer id) {
        List<Cottage> cottages = cottageRepository.getCottagesByCottageOwner_Id(id);
        if (cottages.size() > 0) return cottages;

        List<Ship> ships = shipRepository.getShipsByShipOwner_Id(id);
        if (ships.size() > 0) return ships;

        List<Adventure> adventures = adventureRepository.getAdventuresByFishingInstructor_Id(id);
        if (adventures.size() > 0) return adventures;

        return null;
    }
}
