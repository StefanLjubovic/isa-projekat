package com.backend.controller;

import com.backend.dto.EntityDTO;
import com.backend.dto.SaleDTO;
import com.backend.model.Adventure;
import com.backend.model.RentingEntity;
import com.backend.model.Sale;
import com.backend.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/entity")
public class EntityController {

    @Autowired
    EntityService entityService;

    @GetMapping(value="{state}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<EntityDTO>> getAllEntities(@PathVariable int state){
        Collection<? extends RentingEntity> entities= entityService.GetAllEntities(state);

        Collection<EntityDTO> dto = new ArrayList<>();
        for (RentingEntity e : entities) {
            EntityDTO entityDTO = new EntityDTO(e.getId(), e.getName(), e.getDescription(), e.getAverageGrade(), e.getImages(), e.getAddress());
            dto.add(entityDTO);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/subscriptions",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<? extends RentingEntity>> getSubscriptions(Principal principal){
        Collection<? extends RentingEntity> entities= entityService.GetByUsersSubscriptions(principal.getName());
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @DeleteMapping ("/delete/{id}")
    //@PreAuthorize("hasAnyRole('ADMIN','COTTAGE_OWNER', 'SHIP_OWNER', 'INSTRUCTOR')")
    public ResponseEntity<?> deleteEntityById(@PathVariable("id") Integer id) {
        entityService.deleteEntity(id);
        return new ResponseEntity<>("Entity deleted!", HttpStatus.OK);
    }

    @PostMapping("/sale/{id}")
    public ResponseEntity<List<SaleDTO>> createSaleForEntity(@RequestBody Sale sale, @PathVariable("id") Integer entityId) {
        Set<Sale> sales = entityService.createSaleForEntity(sale, entityId);

        List<SaleDTO> dto = new ArrayList<>();
        for(Sale s : sales) {
            SaleDTO saleDTO = new SaleDTO(s.getId(), s.getDateTimeFrom(), s.getDurationInHours(), s.getMaximumPersons(), s.getExpireDateTime(), s.getAdditionalServices(), s.getPrice());
            dto.add(saleDTO);
        }
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
