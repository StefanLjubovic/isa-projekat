package com.backend.service;

import com.backend.model.*;
import com.backend.repository.IPricelistItemRepository;
import com.backend.repository.IShipRepository;
import com.backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ShipService {

    @Autowired
    private IShipRepository shipRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IPricelistItemRepository pricelistItemRepostory;
    private Base64ToImage imageConverter = new Base64ToImage();

    public ShipService() {}

    public List<Ship> getAll() { return this.shipRepository.findAll(); }

    public Ship findById(Integer id) throws IOException {
        Ship ship = shipRepository.findById(id).get();
        ship.setImages(imageConverter.loadImages(ship.getImages()));
        ship.setUnavailablePeriods(getAllUnavailablePeriodsForCottage(ship.getName()));
        ship.setPricelistItems(getAllPricelistItemsForCottage(ship.getName()));
        ship.setSales(new HashSet<Sale>());
        return ship;
    }

    public Ship findByName(String name) { return this.shipRepository.findByName(name); }

    public List<Ship> getAllShipsFromShipOwner(String email) {
        return shipRepository.getShipsByShipOwner_Email(email);
    }

    public Set<UnavailablePeriod> getAllUnavailablePeriodsForCottage(String cottageName) {
        Ship ship = this.shipRepository.fetchUnavailablePeriodsByName(cottageName);
        return ship.getUnavailablePeriods();
    }

    public Set<PricelistItem> getAllPricelistItemsForCottage(String cottageName) {
        Ship ship = this.shipRepository.fetchPricelistItemsByName(cottageName);
        return ship.getPricelistItems();
    }

    public Ship save(Ship ship) throws IOException {
        RentingEntity entity = createEntityFromShip(ship);
        RegisteredUser user = this.userRepository.findByEmail(ship.getShipOwner().getEmail());
        ShipOwner owner = new ShipOwner(user);
        Ship newShip = new Ship(entity, ship.getType(), ship.getLength(), ship.getEngineNumber(),
                                ship.getEnginePower(), ship.getMaxSpeed(), ship.getNavigationEquipment(),
                                ship.getCapacity(), ship.getFishingEquipment(), owner);

        this.shipRepository.save(newShip);

        Set<PricelistItem> items = ship.getPricelistItems();
        for(PricelistItem item: items){
            item.setRentingEntity(newShip);
            this.pricelistItemRepostory.save(item);
        }

        return newShip;
    }

    private RentingEntity createEntityFromShip(Ship ship) throws IOException {
        Address address = ship.getAddress();
        address.setId(null);
        Set<String> images = saveImages(ship);

        RentingEntity entity = new RentingEntity(ship.getName(), ship.getDescription(),
                ship.getAverageGrade(), ship.getCancellationPercentage(), images,
                ship.getAllowedBehavior(), ship.getUnallowedBehavior(), address);
        return entity;
    }

    private Set<String> saveImages(Ship ship) throws IOException {
        Set<String> convertedImages = new HashSet<String>();
        int i = 1;
        for (String image : ship.getImages()) {
            String basePath = new File("images/").getAbsolutePath();
            String path = basePath + "/ships/" + ship.getName() + i + ".jpg";
            imageConverter.decodeImageFromBase64(image, path);
            String relativePath = "/images/ships/" + ship.getName() + i + ".jpg";
            convertedImages.add(relativePath);
            ++i;
        }
        return convertedImages;
    }
}
