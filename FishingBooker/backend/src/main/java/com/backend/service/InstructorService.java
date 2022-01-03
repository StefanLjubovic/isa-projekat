package com.backend.service;

import com.backend.model.FishingInstructor;
import com.backend.model.UnavailablePeriod;
import com.backend.repository.IAdventureRepository;
import com.backend.repository.IUnavailablePeriodRepository;
import com.backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class InstructorService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IUnavailablePeriodRepository unavailablePeriodRepository;

    public UnavailablePeriod defineUnavailablePeriodForInstructor(UnavailablePeriod unavailablePeriod, String instructorEmail) {
        FishingInstructor fishingInstructor = userRepository.fetchByEmail(instructorEmail);

        for (UnavailablePeriod period : fishingInstructor.getUnavailablePeriods()) {
            if(period.getFromDateTime().before(unavailablePeriod.getToDateTime()) && period.getToDateTime().after(unavailablePeriod.getFromDateTime())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is already defined unavailable period in this range!");
            }
        }

        UnavailablePeriod savedPeriod = unavailablePeriodRepository.save(unavailablePeriod);
        fishingInstructor.getUnavailablePeriods().add(savedPeriod);
        userRepository.save(fishingInstructor);

        return savedPeriod;
    }

    public Set<UnavailablePeriod> getAllUnavailablePeriodsForInstructor(String instructorEmail) {
        FishingInstructor fishingInstructor = userRepository.fetchByEmail(instructorEmail);
        return fishingInstructor.getUnavailablePeriods();
    }

    public Set<UnavailablePeriod> getAllUnavailablePeriodsForInstructorById(Integer id) {
        FishingInstructor fishingInstructor = userRepository.fetchById(id);
        return fishingInstructor.getUnavailablePeriods();
    }
}
