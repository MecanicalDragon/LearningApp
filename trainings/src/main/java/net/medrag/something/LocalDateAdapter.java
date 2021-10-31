package net.medrag.something;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * {@author} Stanislav Tretyakov
 * 14.11.2019
 */
public class LocalDateAdapter {

    public static void main(String[] args) {
        String s = "2019-05-20";
        LocalDate unmarshal = unmarshal(s);
        LocalDateTime date = unmarshal.atStartOfDay();
        System.out.println(date);
    }

    private final static DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .optionalStart()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .toFormatter();

    public static LocalDate unmarshal(String timestamp) {
        return LocalDate.from(formatter.parse(timestamp, LocalDate::from));
    }

    public static String marshal(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
