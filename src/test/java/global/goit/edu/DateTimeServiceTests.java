package global.goit.edu;

import global.goit.edu.datetimeservice.DateTimeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeServiceTests {

    @Test
    public void testThatMethodGetReturnRightDateTime() {

        //Given
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss z")
                .withZone(ZoneId.of("UTC"));

        //When
        String expected = dateTimeFormatter.format(
                Date.from(Instant.now()).toInstant()
        );
        String actual = DateTimeService.get();

        //Then
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testThatMethodGetWithArgumentTimeZoneReturnRightValue() {
        //Given
        String timezone = "UTC+2";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss z")
                .withZone(ZoneId.of(timezone));

        //When
        String actual = DateTimeService.get(timezone);
        String expected = dateTimeFormatter.format(
                Date.from(Instant.now()).toInstant()
        );

        //Then
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testThatMethodGetWithArgumentWithoutPlusInZoneTimeZoneReturnRightValue() {
        //Given
        String timezone = "UTC 2";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss z")
                .withZone(ZoneId.of("UTC+2"));

        //When
        String actual = DateTimeService.get(timezone);
        String expected = dateTimeFormatter.format(
                Date.from(Instant.now()).toInstant()
        );

        //Then
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testThatMethodGetWithInvalidArgumentReturnRightValue() {
        //Given
        String timezone = "UTC+100";
        String expected = "Invalid timezone";

        //When
        String actual = DateTimeService.get(timezone);

        //Then
        Assert.assertEquals(expected, actual);

    }
}