package com.backend.controller;

import com.backend.model.Ship;
import com.backend.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/ship", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShipController {

    @Autowired
    private ShipService shipService;

    public ShipController() {}

    @GetMapping("/getOne/{id}")
    public Ship getOne(@PathVariable("id") Integer id){
        Ship ship = shipService.findById(id);
        System.out.println(ship.getCapacity() + " " + ship.getName());
        return ship;
    }

}
