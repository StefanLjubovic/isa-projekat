package com.backend.constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ReservationConstants {
    public static final Date DB_RESERVATION_DATE= new Date(2021,12,27);
    public static final Date DB_DATE_COTTAGE =  new GregorianCalendar(2015, Calendar.FEBRUARY, 1).getTime();
    public static final Date DB_DATE_ENTITY = parseDate("2014-05-14");
    public static final Date DB_DATE_ENTITY2 = parseDate("2015-05-14");
    public static final Integer DB_RESERVATION_DURATION= 48;
    public static final Integer DB_MAX_PERSONS= 10;
    public static final double DB_PRICE= 2000;
    public static final Integer DB_RESERVATION_DURATION_HISTORY_TEST= 72;
    public static final Long DB_RESERVATION_DATE_HISTORY_TEST = 1641337200000L;
    public static final int DB_MAX_PERSONS_FUTURE_RES = 15;

    public static final Integer DB_RESERVATION_ID = 1;
    public static final Date DB_RESERVATION_DATETIME= new Date();
    public static final int DB_RESERVATION_DURATION_IN_HOURS = 5;
    public static final int DB_RESERVATION_MAX_PERSONS = 5;
    public static final double DB_RESERVATION_PRICE = 2500;

    public static final Date DB_RESERVATION_DATE_2= new Date(2021,12,27);
    public static final Integer DB_RESERVATION_DURATION_2= 48;
    public static final Integer DB_MAX_PERSONS_2= 10;
    public static final double DB_PRICE_2= 2000;

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
