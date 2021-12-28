package com.backend.constants;

import com.backend.model.Role;
import com.backend.model.UserStatus;

import java.sql.Timestamp;
import java.util.Date;

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
}
