package com.backend.controller;

import com.backend.dto.SaleDTO;
import com.backend.model.Sale;
import com.backend.service.EntityService;
import com.backend.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @GetMapping("/instructor")
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ResponseEntity<Set<SaleDTO>> getAllSalesForLoggedInstructor(Principal principal) {
        Set<Sale> sales = saleService.getAllSalesForLoggedInstructor(principal.getName());
        Set<SaleDTO> dto = getSaleDTOS(sales);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/cottage/{name}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
        public ResponseEntity<Set<SaleDTO>> getAllSalesForCottage(@PathVariable("name") String name, Principal principal) {
        Set<Sale> sales = saleService.getAllSalesForCottage(name);
        Set<SaleDTO> dto = getSaleDTOS(sales);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    private Set<SaleDTO> getSaleDTOS(Set<Sale> sales) {
        Set<SaleDTO> dto = new HashSet<>();
        for(Sale s : sales) {
            SaleDTO saleDTO = new SaleDTO(s.getId(), s.getDateTimeFrom(), s.getDurationInHours(), s.getMaximumPersons(), s.getExpireDateTime(), s.getAdditionalServices(), s.getPrice());
            saleDTO.setEntityName(s.getRentingEntity().getName());
            dto.add(saleDTO);
        }
        return dto;
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('COTTAGE_OWNER', 'SHIP_OWNER', 'INSTRUCTOR')")
    public ResponseEntity<SaleDTO> createSaleForEntity(@RequestBody Sale sale, @PathVariable("id") Integer entityId) {
        Sale newSale = saleService.createSaleForEntity(sale, entityId);
        SaleDTO saleDTO = new SaleDTO(newSale.getId(), newSale.getDateTimeFrom(), newSale.getDurationInHours(), newSale.getMaximumPersons(), newSale.getExpireDateTime(), newSale.getAdditionalServices(), newSale.getPrice());
        return new ResponseEntity<>(saleDTO, HttpStatus.CREATED);
    }
}
