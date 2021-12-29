package com.backend.service;

import com.backend.dto.RevisionDTO;
import com.backend.model.Revision;
import com.backend.repository.IEntityRepository;
import com.backend.repository.IReservationRepository;
import com.backend.repository.IRevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RevisionService {

    @Autowired
    IRevisionRepository revisionRepository;
    @Autowired
    IReservationRepository reservationRepository;

    public void saveClientRevision(RevisionDTO dto){
        Revision revision=new Revision(dto.getContent(),dto.getMark(),dto.isApproved(),reservationRepository.findById(dto.getReservationId()).get());
        revisionRepository.save(revision);
    }

}
