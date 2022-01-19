package com.backend.constants;


import java.util.Date;

public class UnavailaiblePeriodConstants {

    public static final Date DB_DATE_BEGIN = ReservationConstants.parseDate("2021-05-14");
    public static final Date DB_DATE_END = ReservationConstants.parseDate("2021-06-14");

    public static final Date DB_DATE_BEGIN_TEST = ReservationConstants.parseDate("2021-06-10");
    public static final Date DB_DATE_END_TEST = ReservationConstants.parseDate("2021-07-10");
    public static final Date DB_RESERVATION_DATE_TEST = ReservationConstants.parseDate("2021-07-20");
    public static final Date DB_SALE_DATE_TEST = ReservationConstants.parseDate("2021-05-20");
}
