package com.backend.service;

import com.backend.model.*;
import com.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SaleService {

    @Autowired
    private IEntityRepository entityRepository;

    @Autowired
    private IAdventureRepository adventureRepository;

    @Autowired
    private ICottageRepository cottageRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IReservationRepository reservationRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ISaleRepository saleRepository;

    public Sale createSaleForEntity(Sale sale, Integer entityId) {
        RentingEntity entity = entityRepository.fetchWithSalesAndPeriods(entityId);

        if (overlapsWithExistingUnavailablePeriod(sale, entity))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is already defined unavailable period in this time range!");
        if (overlapsWithExistingSale(sale, entity))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is already defined sale in this time range!");
        if (overlapsWithExistingReservation(sale, entity))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is already booked reservation in this time range!");

        sale.setRentingEntity(entity);
        entity.getSales().add(sale);
        entityRepository.save(entity);

        sendEmailForSubscribers(sale, entityId);

        return sale;
    }

    public boolean overlapsWithExistingUnavailablePeriod(Sale sale, RentingEntity entity) {
        Adventure adventure = adventureRepository.fetchInstructorByAdventureId(entity.getId());
        if (adventure != null) {

            for (UnavailablePeriod period : userRepository.fetchInstructorWithUnavailablePeriodsById(entity.getId()).getUnavailablePeriods()) {
                if(period.getFromDateTime().before(sale.getSaleEndTime()) && period.getToDateTime().after(sale.getDateTimeFrom()))
                    return true;
            }
        } else {
            for (UnavailablePeriod period : entity.getUnavailablePeriods()) {
                if(period.getFromDateTime().before(sale.getSaleEndTime()) && period.getToDateTime().after(sale.getDateTimeFrom()))
                    return true;
            }
        }

        return false;
    }

    public boolean overlapsWithExistingSale(Sale sale, RentingEntity entity) {
        for(Sale s : entity.getSales()) {
            if (s.getDateTimeFrom().before(sale.getSaleEndTime()) && s.getSaleEndTime().after(sale.getDateTimeFrom()))
                return true;
        }
        return false;
    }

    public boolean overlapsWithExistingReservation(Sale sale, RentingEntity entity) {
        List<Reservation> reservations = reservationRepository.fetchByEntityId(entity.getId());
        for(Reservation r : reservations) {
            if (r.getDateTime().before(sale.getSaleEndTime()) && r.getReservationEndTime().after(sale.getDateTimeFrom()))
                return true;
        }
        return false;
    }

    public Set<Sale> getAllSalesForLoggedInstructor(String email) {
        List<Adventure> adventures = adventureRepository.fetchAdventuresByFishingInstructor_Email(email);

        Set<Sale> sales = new HashSet<>();
        for(Adventure a : adventures) {
            for(Sale s : a.getSales()) {
                s.setRentingEntity(a);
                sales.add(s);
            }
        }
        return sales;
    }

    private void sendEmailForSubscribers(Sale sale, Integer entityId) {
        List<Client> subscribers = userRepository.fetchClientsSubscribedToEntity(entityId);

        for(Client c : subscribers) {
            emailService.sendSimpleMessage(c.getEmail(), "NEW SALE", generateEmailContentForSubscribers(sale, c));
        }
    }

    private String generateEmailContentForSubscribers(Sale sale, Client c) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hello, ").append(c.getFirstName()).append(" ").append(c.getLastName())
                .append("\nThere is new sale on the entity you are subscribed to. Check it out on our website!")
                .append("\nHurry up! Sale expires on ").append(sale.getExpireDateTime());
        return stringBuilder.toString();
    }

    public void delete(Sale sale) {
        saleRepository.delete(sale);
    }

    public Set<Sale> getAllSalesForCottage(String name) {
        Cottage cottage = this.cottageRepository.fetchSalesByName(name);
        return cottage.getSales();
    }
}
