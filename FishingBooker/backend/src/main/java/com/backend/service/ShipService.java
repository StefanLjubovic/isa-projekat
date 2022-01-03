package com.backend.service;

import com.backend.model.*;
import com.backend.repository.IShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ShipService {

    @Autowired
    private IShipRepository shipRepository;

    public ShipService() {}

    public List<Ship> getAllShipsFromShipOwner(String email) {
        return shipRepository.getShipsByShipOwner_Email(email);
    }

    public Ship findById(Integer id) {
        Ship ship = shipRepository.findById(id).get();
        ship.setUnavailablePeriods(getAllUnavailablePeriodsForCottage(ship.getName()));
        ship.setPricelistItems(getAllPricelistItemsForCottage(ship.getName()));
        ship.setSales(new HashSet<Sale>());
        return ship;
    }

    public Set<UnavailablePeriod> getAllUnavailablePeriodsForCottage(String cottageName) {
        Ship ship = this.shipRepository.fetchUnavailablePeriodsByName(cottageName);
        return ship.getUnavailablePeriods();
    }

    public Set<PricelistItem> getAllPricelistItemsForCottage(String cottageName) {
        Ship ship = this.shipRepository.fetchPricelistItemsByName(cottageName);
        return ship.getPricelistItems();
    }
}
