package com.backend.service;

import com.backend.model.*;
import com.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public CottageService() {}

    public List<Cottage> getAll() {
        return cottageRepository.findAll();
    }

    public Cottage findById(int id) {
        return cottageRepository.findById(id).get();
    }

    public Cottage findByName(String name) {
        return cottageRepository.findByName(name);
    }

    public Cottage Save(Cottage cottage) {
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

    private RentingEntity createEntityFromCottage(Cottage cottage) {
        Address address = cottage.getAddress();
        address.setId(null);
        RentingEntity entity = new RentingEntity(cottage.getName(),
                                    cottage.getDescription(),
                                    cottage.getAverageGrade(),
                                    cottage.getCancellationPercentage(),
                                    cottage.getImages(),
                                    cottage.getAllowedBehavior(),
                                    cottage.getUnallowedBehavior(),
                                    address);
        return entity;
    }
}
