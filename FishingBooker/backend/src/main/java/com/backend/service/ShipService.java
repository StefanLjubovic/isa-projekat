package com.backend.service;

import com.backend.dto.UnavailablePeriodDTO;
import com.backend.model.*;
import com.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    private IReservationRepository reservationRepository;
    @Autowired
    private IPricelistItemRepository pricelistItemRepostory;
    @Autowired
    private IUnavailablePeriodRepository unavailablePeriodRepository;

    private Base64ToImage imageConverter = new Base64ToImage();

    public ShipService() {}

    public List<Ship> getAll() { return this.shipRepository.findAll(); }

    public Ship findById(Integer id) throws IOException {
        Ship ship = shipRepository.findById(id).get();
        ship.setImages(imageConverter.loadImages(ship.getImages()));
        ship.setUnavailablePeriods(getAllUnavailablePeriodsForCottage(ship.getName()));
        ship.setPricelistItems(getAllPricelistItemsForCottage(ship.getName()));
        ship.setSales(getAllSalesForShip(ship.getName()));
        return ship;
    }

    public Ship findByName(String name) { return this.shipRepository.findByName(name); }

    public Set<UnavailablePeriod> getAllUnavailablePeriodsForCottage(String cottageName) {
        Ship ship = this.shipRepository.fetchUnavailablePeriodsByName(cottageName);
        return ship.getUnavailablePeriods();
    }

    public Set<PricelistItem> getAllPricelistItemsForCottage(String cottageName) {
        Ship ship = this.shipRepository.fetchPricelistItemsByName(cottageName);
        return ship.getPricelistItems();
    }

    public Set<Sale> getAllSalesForShip(String shipName) {
        Ship ship = this.shipRepository.fetchSalesByName(shipName);
        return ship.getSales();
    }

    public UnavailablePeriod defineUnavailablePeriodForShip(UnavailablePeriodDTO unavailablePeriodDTO) {
        UnavailablePeriod unavailablePeriod = new UnavailablePeriod(unavailablePeriodDTO.getFromDateTime(), unavailablePeriodDTO.getToDateTime());
        Ship ship = this.shipRepository.findByName(unavailablePeriodDTO.getEntityName());

        if(unavailablePeriod.overlapsWithExistingUnavailablePeriods(getAllUnavailablePeriodsForCottage(ship.getName())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is already defined unavailable period in this time range!");

        if (unavailablePeriod.overlapsWithExistingReservations(this.reservationRepository.fetchByEntityName(ship.getName())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is already defined reservation in this time range!");

        if(unavailablePeriod.overlapsWithExistingSales(getAllSalesForShip(ship.getName())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is already defined sale in this time range!");

        this.unavailablePeriodRepository.save(unavailablePeriod);
        Set<UnavailablePeriod> unavailablePeriods = getAllUnavailablePeriodsForCottage(ship.getName());
        unavailablePeriods.add(unavailablePeriod);
        ship.setUnavailablePeriods(unavailablePeriods);
        this.shipRepository.save(ship);
        return unavailablePeriod;
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

    public Ship update(Ship ship) throws IOException {
        Ship shipToUpdate = this.shipRepository.findById(ship.getId()).get();
        shipToUpdate.setName(ship.getName());
        shipToUpdate.setDescription(ship.getDescription());
        shipToUpdate.setCancellationPercentage(ship.getCancellationPercentage());
        shipToUpdate.setImages(this.saveImages(ship));
        shipToUpdate.setAllowedBehavior(ship.getAllowedBehavior());
        shipToUpdate.setUnallowedBehavior(ship.getUnallowedBehavior());
        shipToUpdate.setAddress(ship.getAddress());
        shipToUpdate.setPricelistItems(ship.getPricelistItems());
        for (PricelistItem item : ship.getPricelistItems()) {
            item.setRentingEntity(shipToUpdate);
            this.pricelistItemRepostory.save(item);
        }
        shipToUpdate.setType(ship.getType());
        shipToUpdate.setLength(ship.getLength());
        shipToUpdate.setEngineNumber(ship.getEngineNumber());
        shipToUpdate.setEnginePower(ship.getEnginePower());
        shipToUpdate.setMaxSpeed(ship.getMaxSpeed());
        shipToUpdate.setCapacity(ship.getCapacity());
        shipToUpdate.setNavigationEquipment(ship.getNavigationEquipment());
        shipToUpdate.setFishingEquipment(ship.getFishingEquipment());
        return this.shipRepository.save(shipToUpdate);
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
