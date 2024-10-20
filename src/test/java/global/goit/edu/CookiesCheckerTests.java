package global.goit.edu;

import global.goit.edu.servlets.CookiesChecker;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.when;

public class CookiesCheckerTests {

    @Test
    public void testThatMethodGetTimeZoneWorkOk() {

        //Given
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Cookie[] cookies = new Cookie[] {new Cookie("lastTimezone", "UTC")};

        //When
        when(request.getCookies()).thenReturn(cookies);
        String expected = CookiesChecker.getTimeZone(request);

        //Then
        Assert.assertEquals(expected, cookies[0].getValue());
    }
}