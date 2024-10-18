package global.goit.edu.webfilters;

import global.goit.edu.datetimeservice.DateTimeService;
import global.goit.edu.resolver.ResolverService;
import global.goit.edu.servlets.CookiesChecker;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {
    private ResolverService resolverService;
    private TemplateEngine engine;

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        // Get the real path of the index.html file
        ServletContext context = getServletContext();
        String indexFilePath = context.getRealPath("/templates") + "\\";

        resolverService = new ResolverService(indexFilePath);
        engine = resolverService.getEngine();
        String timezone = req.getParameter("timezone");
        String timeParameter = CookiesChecker.getTimeZone(req);
        String dateTime = "";

        if (Objects.isNull(timezone)) {

            dateTime = getDateTime(timeParameter);
            Context simpleContext = new Context(
                    req.getLocale(),
                    Map.of("currentTime", dateTime)
            );
            engine.process("index", simpleContext, res.getWriter());
            res.getWriter().close();
        } else {

            timezone = DateTimeService.formatTimeZone(timezone);

            try {
                ZoneId zoneId = ZoneId.of(timezone);
            } catch (DateTimeException e) {
                res.setStatus(404);

                dateTime = getDateTime(timeParameter);

                Map<String, Object> params = new HashMap<>();
                params.put("currentTime", dateTime);
                params.put("invalidTimezone", timezone);
                Context simpleContext = new Context(
                        req.getLocale(),
                        params
                );
                engine.process("index", simpleContext, res.getWriter());
                res.getWriter().close();
            }
            chain.doFilter(req, res);
        }
    }

    private static String getDateTime(String timeParameter) {
        String result;
        if (timeParameter.isEmpty()) {
            result = DateTimeService.get();
        } else {
            result = DateTimeService.get(timeParameter);
        }
        return result;
    }
}