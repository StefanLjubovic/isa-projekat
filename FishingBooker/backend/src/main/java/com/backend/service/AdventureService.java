package com.backend.service;

import com.backend.model.*;
import com.backend.repository.IAdventureRepository;
import com.backend.repository.IPricelistItemRepository;
import com.backend.repository.IReservationRepository;
import com.backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdventureService {

    @Autowired
    private IAdventureRepository adventureRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IPricelistItemRepository pricelistItemRepostory;

    @Autowired
    private InstructorService instructorService;

    private Base64ToImage imageConverter = new Base64ToImage();

    public Adventure fetchById(Integer id) throws  IOException {
        Adventure adventure = adventureRepository.fetchById(id);
        adventure.setUnavailablePeriods(new HashSet<>());
        return adventure;
    }

    @Cacheable("adventure")
    public Adventure findById(Integer id) {
        return adventureRepository.findById(id).get();
    }

    public List<Adventure> getAllAdventuresFromInstructor(String email) {
        return adventureRepository.getAdventuresByFishingInstructor_Email(email);
    }

    public Adventure findByName(String name) {
        return adventureRepository.findAdventureByName(name);
    }

    @Transactional
    public void save(Adventure adventure) throws IOException {
        //Set<String> images = saveImages(adventure);
        //adventure.setImages(images);

        RegisteredUser user = userRepository.findByEmail(adventure.getFishingInstructor().getEmail());
        FishingInstructor instructor = (FishingInstructor) user;
        adventure.setFishingInstructor(instructor);

        for(UnavailablePeriod up : instructorService.getAllUnavailablePeriodsForInstructor(instructor.getEmail())) {
            UnavailablePeriod unavailablePeriod = new UnavailablePeriod(up.getFromDateTime(), up.getToDateTime());
            adventure.getUnavailablePeriods().add(unavailablePeriod);
        }

        adventureRepository.save(adventure);

        Set<PricelistItem> items = adventure.getPricelistItems();
        for(PricelistItem item: items){
            item.setRentingEntity(adventure);
            this.pricelistItemRepostory.save(item);
        }
    }

    private Set<String> saveImages(Adventure adventure) throws IOException {
        Set<String> convertedImages = new HashSet<String>();
        int i = 1;
        for (String image : adventure.getImages()) {
            String basePath = new File("images/").getAbsolutePath();
            String path = basePath + "/adventures/" + adventure.getName() + i + ".jpg";
            imageConverter.decodeImageFromBase64(image, path);
            String relativePath = "/images/adventures/" + adventure.getName() + i + ".jpg";
            convertedImages.add(relativePath);
            ++i;
        }
        return convertedImages;
    }

    private Set<String> loadImages(Set<String> images) throws IOException {
        Set<String> base64Images = new HashSet<String>();
        for (String image: images) {
            String base64Image = imageConverter.encodeImageToBase64(image);
            base64Images.add(base64Image);
        }
        return  base64Images;
    }

    @CachePut(cacheNames = "adventure", key = "#adventure.id")
    public Adventure update(Adventure adventure) throws IOException {
        Adventure adventureToUpdate = this.findById(adventure.getId());
        adventureToUpdate.setName(adventure.getName());
        adventureToUpdate.setDescription(adventure.getDescription());
        adventureToUpdate.setMaxPersons(adventure.getMaxPersons());
        adventureToUpdate.setCancellationPercentage(adventure.getCancellationPercentage());
        adventureToUpdate.setAllowedBehavior(adventure.getAllowedBehavior());
        adventureToUpdate.setUnallowedBehavior(adventure.getUnallowedBehavior());
        adventureToUpdate.setFishingEquipment(adventure.getFishingEquipment());
        adventureToUpdate.setImages(adventure.getImages());

        adventureToUpdate.setPricelistItems(adventure.getPricelistItems());
        for (PricelistItem item : adventureToUpdate.getPricelistItems()){
            item.setRentingEntity(adventureToUpdate);
        }

        adventureToUpdate.setAddress(adventure.getAddress());

        return adventureRepository.save(adventureToUpdate);
    }
}
