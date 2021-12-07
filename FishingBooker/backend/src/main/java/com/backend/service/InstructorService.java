package com.backend.service;

import com.backend.model.Adventure;
import com.backend.model.FishingInstructor;
import com.backend.model.RegisteredUser;
import com.backend.model.UnavailablePeriod;
import com.backend.repository.IAdventureRepository;
import com.backend.repository.IUnavailablePeriodRepository;
import com.backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IAdventureRepository adventureRepository;

    @Autowired
    private IUnavailablePeriodRepository unavailablePeriodRepository;

    // ne radi!
    public void defineUnavailablePeriodForInstructor(UnavailablePeriod unavailablePeriod, String instructorEmail) {
        FishingInstructor fishingInstructor = (FishingInstructor) userRepository.findByEmail(instructorEmail);

        unavailablePeriodRepository.save(unavailablePeriod);
        fishingInstructor.getUnavailablePeriod().add(unavailablePeriod);
        userRepository.save(fishingInstructor);
    }
}
