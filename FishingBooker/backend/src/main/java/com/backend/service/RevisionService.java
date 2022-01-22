package com.backend.service;

import com.backend.dto.RevisionDTO;
import com.backend.model.RegisteredUser;
import com.backend.model.RentingEntity;
import com.backend.model.Reservation;
import com.backend.model.Revision;
import com.backend.repository.IEntityRepository;
import com.backend.repository.IReservationRepository;
import com.backend.repository.IRevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RevisionService {

    @Autowired
    private IRevisionRepository revisionRepository;

    @Autowired
    private IReservationRepository reservationRepository;

    @Autowired
    private IEntityRepository entityRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AdvertiserService advertiserService;

    public void saveClientRevision(RevisionDTO dto){
        Revision revision=new Revision(dto.getContent(),dto.getMark(),dto.isApproved(),reservationRepository.findById(dto.getReservationId()).get());
        revisionRepository.save(revision);
    }

    public List<Revision> getAllPendingRevisions() {
        return revisionRepository.findAllByOrderByIdAsc().stream().filter(r -> r.getApproved() == Boolean.FALSE).collect(Collectors.toList());
    }

    public Revision getById(Integer id) {
        return revisionRepository.findById(id).get();
    }

    @Transactional
    public void deleteById(Integer id) throws NoSuchObjectException {
        Revision revision = revisionRepository.findOneById(id);
        if (revision == null) throw new NoSuchObjectException("No such revision");
        revisionRepository.delete(revision);
    }

    @Transactional
    public void approveRevision(Integer id) throws NoSuchObjectException {
        Revision revision = revisionRepository.findOneById(id);
        if (revision == null || revision.getApproved()) throw new NoSuchObjectException("No such revision");

        revision.setApproved(true);
        revisionRepository.save(revision);

        RentingEntity reviewedEntity = entityRepository.findById(revision.getReservation().getRentingEntity().getId()).get();
        double newAverageGrade = revisionRepository.getAverageGradeOfEntityById(reviewedEntity.getId());
        reviewedEntity.setAverageGrade(newAverageGrade);
        entityRepository.save(reviewedEntity);

        sendEmailToAdvertiser(revision, newAverageGrade);
    }

    private void sendEmailToAdvertiser(Revision revision, double newAverageGrade) {
        RegisteredUser advertiser = advertiserService.findAdvertiserByEntityId(revision.getReservation().getRentingEntity().getId());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hello, ").append(advertiser.getFirstName()).append(" ").append(advertiser.getLastName())
                .append("\nNew revision for your service ").append(revision.getReservation().getRentingEntity().getName()).append(" is approved.")
                .append("\nRevision content: ").append(revision.getContent())
                .append("\nGrade: ").append(revision.getMark())
                .append("\nNew average grade is ").append(newAverageGrade);

        emailService.sendSimpleMessage(advertiser.getEmail(), "New revision approved", stringBuilder.toString());
    }
}
