package com.backend.service;

import com.backend.model.*;
import com.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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

    private Base64ToImage decoder = new Base64ToImage();

    public CottageService() {}

    public List<Cottage> getAll() {
        return cottageRepository.findAll();
    }

    public Cottage findById(int id) throws IOException {
        Cottage cottage = cottageRepository.findById(id).get();
        cottage.setImages(sendImageAsByteArray(cottage.getImages()));
        System.out.println(cottage.getImages());
        return cottage;
    }

    public Cottage findByName(String name) {
        return cottageRepository.findByName(name);
    }

    public Cottage Save(Cottage cottage) throws IOException {
        RentingEntity entity = createEntityFromCottage(cottage);
        RegisteredUser user = this.userRepository.findByEmail("marijakljestan@gmail.com");
        CottageOwner owner = new CottageOwner(user);
        Cottage newCottage = new Cottage(entity, owner);
        this.cottageRepository.save(newCottage);

        Set<Room> rooms = cottage.getRooms();
        for (Room room: rooms) {
            room.setCottage(newCottage);
            this.roomRepostirory.save(room);
        }
        return newCottage;
    }

    private RentingEntity createEntityFromCottage(Cottage cottage) throws IOException {
        Address address = cottage.getAddress();
        address.setId(null);

        Set<String> images = convertImages(cottage);

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

    private Set<String> convertImages(Cottage cottage) throws IOException {
        Set<String> convertedImages = new HashSet<String>();
        int i = 1;
        for (String s : cottage.getImages()) {
            String basePath = new File("images/").getAbsolutePath();
            String path = basePath + "/cottages/" + cottage.getName() + i + ".jpg";
            System.out.println(path);
            decoder.Base64DecodeAndSave(s, path);
            String pathDB = "/images/cottages/" + cottage.getName() + i + ".jpg";
            System.out.println(path.length());
            convertedImages.add(pathDB);
            ++i;
        }
        return convertedImages;
    }

    private Set<String> sendImageAsByteArray(Set<String> images) throws IOException {

        Set<String> imagesFrontend = new HashSet<String>();
        for (String image: images) {
            String imageAsString = decoder.readImagesFromPath(image);
            imagesFrontend.add(imageAsString);
        }
        return  imagesFrontend;
    }
}
