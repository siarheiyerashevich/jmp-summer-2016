package com.epam.jmp.fileshare.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nbuny on 21.07.2016.
 */
public class FileShareDateFormatter {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String format(final Date date) {
        return dateFormat.format(date);
    }

    public static Date parse(final String value) {
        try {
            return dateFormat.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }
}
