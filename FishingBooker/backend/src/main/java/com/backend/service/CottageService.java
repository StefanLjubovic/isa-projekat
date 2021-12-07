package com.backend.service;

import com.backend.model.Cottage;
import com.backend.repository.ICottageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CottageService {

    @Autowired
    private ICottageRepository cottageRepository;

    public CottageService() {}

    public List<Cottage> getAll() {
        return cottageRepository.findAll();
    }

    public Cottage findById(int id) {
        return cottageRepository.findById(id).get();
    }
}
