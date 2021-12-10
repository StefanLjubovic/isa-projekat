package com.backend.controller;

import com.backend.dto.CottageDTO;
import com.backend.dto.UpdateCottageDTO;
import com.backend.model.Cottage;
import com.backend.service.CottageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value="/cottage", produces = MediaType.APPLICATION_JSON_VALUE)
public class CottageController {

    @Autowired
    private CottageService cottageService;
    private ModelMapper modelMapper = new ModelMapper();

    public CottageController() {}

    @GetMapping
    public List<Cottage> getAllCottages(){
        List<Cottage> allCottages = cottageService.getAll();
        return allCottages;
    }

    @GetMapping("/getOne/{id}")
    public Cottage getOne (@PathVariable("id") Integer id) throws IOException {
        return cottageService.findById(id);
    }

    @PostMapping("/add")
    //@PreAuthorize("hasAnyRole('COTTAGE_OWNER')")
    public ResponseEntity<String> addNewCottage(@RequestBody CottageDTO cottageDTO) throws IOException {
        Cottage cottage = modelMapper.map(cottageDTO, Cottage.class);
        if(cottageService.findByName(cottage.getName()) != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cottage with this name already exists!");

        Cottage newCottage = cottageService.Save(cottage);
        return new ResponseEntity<>("Successfully added cottage!", HttpStatus.CREATED);
    }

    @PutMapping("/update")
    //@PreAuthorize("hasAnyRole('COTTAGE_OWNER')")
    public ResponseEntity<String> updateCottage(@RequestBody UpdateCottageDTO updateDTO) throws IOException {
        Cottage cottage = modelMapper.map(updateDTO, Cottage.class);
        if(existCottageWithSameName(cottage))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cottage with this name already exists!");

        this.cottageService.update(cottage);
        return new ResponseEntity<>("Successfully edited cottage!", HttpStatus.OK);
    }

    private boolean existCottageWithSameName(Cottage cottage) {
        Cottage existedCottage = this.cottageService.findByName(cottage.getName());
        return existedCottage != null && existedCottage.getId() != cottage.getId();
    }
}
