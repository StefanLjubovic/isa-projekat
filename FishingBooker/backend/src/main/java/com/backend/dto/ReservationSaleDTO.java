package com.backend.dto;

import com.backend.model.Reservation;
import com.backend.model.Sale;

public class ReservationSaleDTO {
    Reservation reservation;
    Sale sale;

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }
}
