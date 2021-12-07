package com.backend.controller;

import com.backend.config.ModelMapperConfig;
import com.backend.dto.CottageDTO;
import com.backend.model.Cottage;
import com.backend.service.CottageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value="/cottage", produces = MediaType.APPLICATION_JSON_VALUE)
public class CottageController {

    @Autowired
    private CottageService cottageService;

    private ModelMapper modelMapper = new ModelMapper();

    public CottageController() {
    }

    @GetMapping
    public List<Cottage> getAllCottages(){
        List<Cottage> allCottages = cottageService.getAll();
        return allCottages;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNewCottage(@RequestBody CottageDTO cottageDTO){

        Cottage cottage = modelMapper.map(cottageDTO, Cottage.class);
        System.out.println(cottageDTO.getRooms());
        //if(cottageService.findById(cottage.getId()) != null)
        //    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cottage already exists!");

        //Cottage newCottage = cottageService.Save(cottage);
        return new ResponseEntity<>("Successfully added cottage!", HttpStatus.CREATED);
    }
}
