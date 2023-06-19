package com.codekaidev.uploaddownloadfiles.util.date;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class LocalDateUtil {

    private static final String DD_MM_4Y_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";

    public static LocalDateTime toLocalDateTimeFormat(LocalDateTime date, LocalDateFormat pattern){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern.getPattern());
        String formattedDate = date.format(formatter);
        return LocalDateTime.parse(formattedDate, formatter);
    }

}
