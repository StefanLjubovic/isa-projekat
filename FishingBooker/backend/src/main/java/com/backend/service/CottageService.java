package com.backend.service;

import com.backend.dto.UnavailablePeriodDTO;
import com.backend.model.*;
import com.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class CottageService {
    @Autowired
    private ICottageRepository cottageRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IReservationRepository reservationRepository;

    @Autowired
    private IPricelistItemRepository pricelistItemRepostory;

    @Autowired
    private IUnavailablePeriodRepository unavailablePeriodRepository;

    @Autowired
    private IEntityRepository entityRepository;

    private Base64ToImage imageConverter = new Base64ToImage();

    public CottageService() {}

    public List<Cottage> getAll() { return cottageRepository.findAll(); }

    public Cottage findById(int id) throws IOException {
        Cottage cottage = cottageRepository.findById(id).get();
        cottage.setImages(imageConverter.loadImages(cottage.getImages()));
        cottage.setUnavailablePeriods(getAllUnavailablePeriodsForCottage(cottage.getName()));
        cottage.setPricelistItems(getAllPricelistItemsForCottage(cottage.getName()));
        cottage.setRooms(getAllRoomsForCottage(cottage.getName()));
        cottage.setSales(getAllSalesForCottage(cottage.getName()));
        return cottage;
    }
    @Cacheable("cottage")
    public Cottage findByName(String name) {
        return cottageRepository.findByName(name);
    }

    public Cottage save(Cottage cottage) throws IOException {
        RentingEntity entity = createEntityFromCottage(cottage);
        RegisteredUser user = this.userRepository.findByEmail(cottage.getCottageOwner().getEmail());
        CottageOwner owner = new CottageOwner(user);
        Cottage newCottage = new Cottage(entity, owner);
        newCottage.setRooms(cottage.getRooms());
        this.cottageRepository.save(newCottage);

        Set<PricelistItem> items = cottage.getPricelistItems();
        for(PricelistItem item: items){
            item.setRentingEntity(newCottage);
            this.pricelistItemRepostory.save(item);
        }
        return newCottage;
    }

    @CachePut(cacheNames = "cottage", key = "#product.id")
    public Cottage update (Cottage cottage) throws IOException {
        Cottage cottageToUpdate = this.cottageRepository.findById(cottage.getId()).get();
        cottageToUpdate.setName(cottage.getName());
        cottageToUpdate.setDescription(cottage.getDescription());
        cottageToUpdate.setCancellationPercentage(cottage.getCancellationPercentage());
        cottageToUpdate.setRooms(cottage.getRooms());
        cottageToUpdate.setAllowedBehavior(cottage.getAllowedBehavior());
        cottageToUpdate.setUnallowedBehavior(cottage.getUnallowedBehavior());
        cottageToUpdate.setPricelistItems(cottage.getPricelistItems());
        for (PricelistItem item : cottageToUpdate.getPricelistItems()) {
            item.setRentingEntity(cottageToUpdate);
            this.pricelistItemRepostory.save(item);
        }
        cottageToUpdate.setAddress(cottage.getAddress());
        cottageToUpdate.setImages(this.saveImages(cottage));

        return this.cottageRepository.save(cottageToUpdate);
    }

    public Set<Room> getAllRoomsForCottage(String cottageName) {
        Cottage cottage = this.cottageRepository.fetchRoomsByName(cottageName);
        return cottage.getRooms();
    }

    public Set<UnavailablePeriod> getAllUnavailablePeriodsForCottage(String cottageName) {
        Cottage cottage = this.cottageRepository.fetchUnavailablePeriodsByName(cottageName);
        return cottage.getUnavailablePeriods();
    }

    public Set<PricelistItem> getAllPricelistItemsForCottage(String cottageName) {
        Cottage cottage = this.cottageRepository.fetchPricelistItemsByName(cottageName);
        return cottage.getPricelistItems();
    }

    public Set<Sale> getAllSalesForCottage(String cottageName) {
        Cottage cottage = this.cottageRepository.fetchSalesByName(cottageName);
        return cottage.getSales();
    }

    private RentingEntity createEntityFromCottage(Cottage cottage) throws IOException {
        Address address = cottage.getAddress();
        address.setId(null);
        Set<String> images = saveImages(cottage);

        RentingEntity entity = new RentingEntity(cottage.getName(),
                                    cottage.getDescription(),
                                    cottage.getAverageGrade(),
                                    cottage.getCancellationPercentage(),
                                    images,
                                    cottage.getAllowedBehavior(),
                                    cottage.getUnallowedBehavior(),
                                    address);
        return entity;
    }

    @Transactional
    public UnavailablePeriod defineUnavailablePeriodForCottage(UnavailablePeriodDTO unavailablePeriodDTO) {
        UnavailablePeriod unavailablePeriod = new UnavailablePeriod(unavailablePeriodDTO.getFromDateTime(), unavailablePeriodDTO.getToDateTime());
        RentingEntity entity;
        try{
             entity = this.entityRepository.findLockedById(unavailablePeriodDTO.getEntityId());
        } catch(PessimisticLockingFailureException ex) { throw  new PessimisticLockingFailureException("Client already reserved this entity!"); }

        if(unavailablePeriod.overlapsWithExistingUnavailablePeriods(getAllUnavailablePeriodsForCottage(entity.getName())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is already defined unavailable period in this time range!");

        if (unavailablePeriod.overlapsWithExistingReservations(this.reservationRepository.fetchByEntityName(entity.getName())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is already defined reservation in this time range!");

        if(unavailablePeriod.overlapsWithExistingSales(getAllSalesForCottage(entity.getName())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is already defined sale in this time range!");

        this.unavailablePeriodRepository.save(unavailablePeriod);
        Set<UnavailablePeriod> unavailablePeriods = getAllUnavailablePeriodsForCottage(entity.getName());
        unavailablePeriods.add(unavailablePeriod);
        entity.setUnavailablePeriods(unavailablePeriods);
        this.entityRepository.save(entity);
        return unavailablePeriod;
    }

    private Set<String> saveImages(Cottage cottage) throws IOException {
        Set<String> convertedImages = new HashSet<String>();
        int i = 1;
        for (String image : cottage.getImages()) {
            String basePath = new File("images/").getAbsolutePath();
            String path = basePath + "/cottages/" + cottage.getName() + i + ".jpg";
            imageConverter.decodeImageFromBase64(image, path);
            String relativePath = "/images/cottages/" + cottage.getName() + i + ".jpg";
            convertedImages.add(relativePath);
            ++i;
        }
        return convertedImages;
    }
}
