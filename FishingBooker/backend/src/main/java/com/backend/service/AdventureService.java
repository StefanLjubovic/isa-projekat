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

    @Cacheable("adventure")
    public Adventure getById(Integer id) throws  IOException {
        Adventure adventure = adventureRepository.fetchById(id);
        adventure.setImages(loadImages(adventure.getImages()));
        adventure.setUnavailablePeriods(new HashSet<>());
        return adventure;
    }

    public List<Adventure> getAllAdventuresFromInstructor(String email) {
        return adventureRepository.getAdventuresByFishingInstructor_Email(email);
    }

    @Cacheable("adventure")
    public Adventure findByName(String name) {
        return adventureRepository.findAdventureByName(name);
    }

    public void save(Adventure adventure) throws IOException {
        Set<String> images = saveImages(adventure);
        adventure.setImages(images);

        RegisteredUser user = userRepository.findByEmail(adventure.getFishingInstructor().getEmail());
        FishingInstructor instructor = (FishingInstructor) user;
        adventure.setFishingInstructor(instructor);
        adventure.setUnavailablePeriods(instructorService.getAllUnavailablePeriodsForInstructor(instructor.getEmail()));

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
        Adventure adventureToUpdate = adventureRepository.findById(adventure.getId()).get();
        adventureToUpdate.setName(adventure.getName());
        adventureToUpdate.setDescription(adventure.getDescription());
        adventureToUpdate.setMaxPersons(adventure.getMaxPersons());
        adventureToUpdate.setCancellationPercentage(adventure.getCancellationPercentage());
        adventureToUpdate.setAllowedBehavior(adventure.getAllowedBehavior());
        adventureToUpdate.setUnallowedBehavior(adventure.getUnallowedBehavior());
        adventureToUpdate.setFishingEquipment(adventure.getFishingEquipment());
        adventureToUpdate.setImages(saveImages(adventure));

        adventureToUpdate.setPricelistItems(adventure.getPricelistItems());
        for (PricelistItem item : adventureToUpdate.getPricelistItems()){
            item.setRentingEntity(adventureToUpdate);
        }

        adventureToUpdate.setAddress(adventure.getAddress());

        return adventureRepository.save(adventureToUpdate);
    }
}
