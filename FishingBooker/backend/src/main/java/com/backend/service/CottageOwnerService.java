package com.backend.service;

import com.backend.model.Reservation;
import com.backend.repository.ICottageRepository;
import com.backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class CottageOwnerService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICottageRepository cottageRepository;
}
