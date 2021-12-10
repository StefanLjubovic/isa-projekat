package com.backend.service;

import com.backend.model.*;
import com.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

@Service
public class CottageService {
    @Autowired
    private ICottageRepository cottageRepository;
    @Autowired
    private IRoomRepository roomRepostirory;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IPricelistItemRepository pricelistItemRepostory;
    private Base64ToImage imageConverter = new Base64ToImage();

    public CottageService() {}

    public List<Cottage> getAll() { return cottageRepository.findAll(); }

    public Cottage findById(int id) throws IOException {
        Cottage cottage = cottageRepository.findById(id).get();
        cottage.setImages(loadImages(cottage.getImages()));
        cottage.setUnavailablePeriods(getAllUnavailablePeriodsForCottage(cottage.getName()));
        cottage.setPricelistItems(getAllPricelistItemsForCottage(cottage.getName()));
        cottage.setRooms(getAllRoomsForCottage(cottage.getName()));
        cottage.setSales(new HashSet<Sale>());
        return cottage;
    }

    public Cottage findByName(String name) {
        return cottageRepository.findByName(name);
    }

    public Cottage Save(Cottage cottage) throws IOException {
        RentingEntity entity = createEntityFromCottage(cottage);
        RegisteredUser user = this.userRepository.findByEmail(cottage.getCottageOwner().getEmail());
        CottageOwner owner = new CottageOwner(user);
        Cottage newCottage = new Cottage(entity, owner);
        newCottage.setRooms(cottage.getRooms());
        this.cottageRepository.save(newCottage);

        Set<Room> rooms = cottage.getRooms();
        for (Room room: rooms)
            this.roomRepostirory.save(room);

        Set<PricelistItem> items = cottage.getPricelistItems();
        for(PricelistItem item: items){
            item.setRentingEntity(newCottage);
            this.pricelistItemRepostory.save(item);
        }
        return newCottage;
    }

    public Cottage update (Cottage cottage){
        Cottage cottageToUpdate = this.cottageRepository.findById(cottage.getId()).get();
        cottageToUpdate.setName(cottage.getName());
        cottageToUpdate.setDescription(cottage.getDescription());
        cottageToUpdate.setCancellationPercentage(cottage.getCancellationPercentage());
        cottageToUpdate.setRooms(cottage.getRooms());
        cottageToUpdate.setAllowedBehavior(cottage.getAllowedBehavior());
        cottageToUpdate.setUnallowedBehavior(cottage.getUnallowedBehavior());
        cottageToUpdate.setPricelistItems(cottage.getPricelistItems());
        for (PricelistItem item : cottageToUpdate.getPricelistItems())
            item.setRentingEntity(cottage);

        cottageToUpdate.setAddress(cottage.getAddress());

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

    private Set<String> loadImages(Set<String> images) throws IOException {
        Set<String> base64Images = new HashSet<String>();
        for (String image: images) {
            String base64Image = imageConverter.encodeImageToBase64(image);
            base64Images.add(base64Image);
        }
        return  base64Images;
    }
}
