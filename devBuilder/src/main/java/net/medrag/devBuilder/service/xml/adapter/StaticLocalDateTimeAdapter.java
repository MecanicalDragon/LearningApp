package net.medrag.devBuilder.service.xml.adapter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;

/**
 * {@author} Stanislav Tretyakov
 * 06.11.2019
 */
public class StaticLocalDateTimeAdapter {
    public static LocalDateTime unmarshal(String timestamp) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .optionalStart()
                .append(DateTimeFormatter.ISO_LOCAL_DATE)
                .appendLiteral('T')
                .optionalEnd()
                .append(DateTimeFormatter.ISO_LOCAL_TIME)
                .optionalStart()
                .appendOffsetId()
                .optionalStart()
                .parseCaseSensitive()
                .appendZoneRegionId()
                .appendLiteral('Z')
                .toFormatter();
        TemporalAccessor temporalAccessor = formatter.parseBest(timestamp, ZonedDateTime::from, LocalDateTime::from);
        if (temporalAccessor instanceof ZonedDateTime) {
            return ZonedDateTime.from(temporalAccessor).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
        } else {
            return LocalDateTime.from(temporalAccessor);
        }
    }

    public static String marshal(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }
}
