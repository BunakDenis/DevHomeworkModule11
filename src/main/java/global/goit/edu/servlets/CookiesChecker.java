package global.goit.edu.servlets;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookiesChecker {

// Метод проверяет наличие атрибута lastTimezone в Cookie
    public static String getTimeZone(HttpServletRequest request) {
        String result = "";

        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("lastTimezone")) {
                result = cookie.getValue();
            }
        }
        return result;
    }
}
