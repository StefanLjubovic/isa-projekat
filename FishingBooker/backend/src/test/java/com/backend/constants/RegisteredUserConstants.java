package com.backend.constants;

import com.backend.model.UserStatus;
import java.sql.Timestamp;

public class RegisteredUserConstants {
    public static final Integer DB_ID = 1;
    public static final String DB_FIRST_NAME = "Stevan";
    public static final String DB_LAST_NAME = "Stević";
    public static final String DB_PHONE_NUMBER = "066103130";
    public static final String DB_EMAIL = "ljubovicstefan@gmail.com";
    public static final String DB_PASSWORD= "123456";
    public static final UserStatus DB_USER_STATUS =UserStatus.active;
    public static final boolean DB_ENABLED= true;
    public static final String DB_ROLE_NAME= "ROLE_CLIENT";
    public static final Timestamp DB_DATE= new Timestamp(System.currentTimeMillis());

    public static final String DB_FIRST_NAME_SECOND = "Pera";
    public static final String DB_LAST_NAME_SECOND = "Peric";
    public static final String DB_EMAIL_SECOND = "pera@gmail.com";

    public static final Integer DB_ID_THIRD = 3;
    public static final String DB_FIRST_NAME_THIRD = "Marija";
    public static final String DB_LAST_NAME_THIRD = "Kljestan";
    public static final String DB_PHONE_NUMBER_THIRD = "0651234567";
    public static final String DB_EMAIL_THIRD = "marijakljestan@gmail.com";
    public static final String DB_PASSWORD_THIRD= "123456";
    public static final UserStatus DB_USER_STATUS_THIRD =UserStatus.active;
    public static final boolean DB_ENABLED_THIRD= true;
    public static final String DB_ROLE_NAME_THIRD= "ROLE_COTTAGE_OWNER";
    public static final Timestamp DB_DATE_THIRD= new Timestamp(System.currentTimeMillis());

    public static final Integer DB_ID_REGISTER = 5;
    public static final String DB_FIRST_NAME_REGISTER = "Mika";
    public static final String DB_LAST_NAME_REGISTER = "Mikic";
    public static final String DB_PHONE_NUMBER_REGISTER = "0651234567";
    public static final String DB_EMAIL_REGISTER = "mikamikic@gmail.com";
    public static final String DB_PASSWORD_REGISTER= "123456";
    public static final UserStatus DB_USER_STATUS_REGISTER =UserStatus.active;
    public static final boolean DB_ENABLED_REGISTER= true;
    public static final String DB_BIOGRAPHY_REGISTER= "Student at FTN";
    public static final String DB_EXPLANATION_REGISTER= "Explanation";
    public static final String DB_ROLE_NAME_REGISTER= "ROLE_SHIP_OWNER";
    public static final Timestamp DB_DATE_REGISTER= new Timestamp(System.currentTimeMillis());
}
