package global.goit.edu.datetimeservice;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeService {
    private static final String DATE_TIME_PATTERN = "dd-MM-yyyy HH:mm:ss z";
    private static final String DEFAULT_TIME_ZONE = "UTC";

    private static Date date;

    public static String get() {
        date = Date.from(Instant.now());
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)
                .withZone(ZoneId.of(DEFAULT_TIME_ZONE));

        String result = dateTimeFormat.format(date.toInstant());

        return result;
    }

    public static String get(String utc) {
        date = Date.from(Instant.now());
        String result = "Invalid timezone";
        String timeZone = formatTimeZone(utc);

        try {
            DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)
                    .withZone(ZoneId.of(timeZone));
            System.out.println(ZoneId.of(timeZone));
            result = dateTimeFormat.format(date.toInstant());
        } catch (DateTimeException e) {

        }
        return result;
    }

    public static String formatTimeZone(String utc) {
        StringBuilder timeZone = new StringBuilder(utc.trim());

        if (!utc.equals(DEFAULT_TIME_ZONE)) {
            if (timeZone.indexOf("+") == -1) {
                timeZone.setCharAt(timeZone.indexOf("C") + 1, '+');
            }
        }
        return timeZone.toString();
    }
}