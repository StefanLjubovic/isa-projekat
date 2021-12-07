package com.backend.service;

import com.backend.model.FishingInstructor;
import com.backend.model.UnavailablePeriod;
import com.backend.repository.IAdventureRepository;
import com.backend.repository.IUnavailablePeriodRepository;
import com.backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        FishingInstructor fishingInstructor = userRepository.fetchByEmail(instructorEmail);

        UnavailablePeriod savedPeriod = unavailablePeriodRepository.save(unavailablePeriod);
        fishingInstructor.getUnavailablePeriods().add(savedPeriod);
        userRepository.save(fishingInstructor);
    }
}
