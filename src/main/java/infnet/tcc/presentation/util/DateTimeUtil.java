/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.presentation.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author peof
 */
public class DateTimeUtil {
    
    public static Date getCurrentDate() {
        Instant instant = Instant.now();
        Locale locale = new Locale("pt", "BR");
        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
        ZonedDateTime zonedDT = instant.atZone(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                .withLocale(locale);

        zonedDT.format(formatter);

        Calendar calendar;
        calendar = GregorianCalendar.from(zonedDT);

        return calendar.getTime();
    }
}
