package com.FWRP.utils;

import java.math.BigDecimal;
import java.sql.Date;

public final class FormParser {

    public static Date parseExpiry(String expiryDateString) {
        String[] yyyymmdd = expiryDateString.split("-");
        int year = Integer.parseInt(yyyymmdd[0]) - 1900;
        int month = Integer.parseInt(yyyymmdd[1]) - 1;
        int day = Integer.parseInt(yyyymmdd[2]);
        return new Date(year, month, day);
    }

    public static BigDecimal parseNullablePrice(String price) {
        if (price != null && !price.isEmpty() && !price.isBlank()) {
            return new BigDecimal(price);
        }
        return null;
    }
}
