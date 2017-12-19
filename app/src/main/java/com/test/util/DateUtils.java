package com.test.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Performs the Date format operation.
 */
public class DateUtils {
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy/MM/dd";
    public static final String DATE_FORMAT_YYYY_MM_DD_OSP = "yyyy-MM-dd";
    public static final String DATE_FORMAT_DD_MM_YYYY = "dd/MM/yyyy";

    public static String convertDate(final String strSourceFormat, final String strDestinationFormat, final String sourceDate) {
        final SimpleDateFormat sourceFormat = new SimpleDateFormat(strSourceFormat, Locale.getDefault());
        final SimpleDateFormat DesiredFormat = new SimpleDateFormat(strDestinationFormat, Locale.getDefault());

        final Date date;
        try {
            date = sourceFormat.parse(sourceDate);

            return DesiredFormat.format(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
