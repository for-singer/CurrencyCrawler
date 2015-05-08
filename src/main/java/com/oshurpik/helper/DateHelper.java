package com.oshurpik.helper;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.springframework.stereotype.Component;

@Component
public class DateHelper {
    public String toDate(long timestamp) {
        Date date = new Date(timestamp);
        DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }
}
