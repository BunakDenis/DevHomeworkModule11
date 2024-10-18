package global.goit.edu.servlets;

import global.goit.edu.datetimeservice.DateTimeService;
import global.goit.edu.resolver.ResolverService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "time-servlet", value = "/time")
public class TimeServlet extends HttpServlet {
    private ResolverService resolverService;
    private TemplateEngine engine;

    @Override
    public void init() throws ServletException {
        // Get the real path of the index.html file
        ServletContext context = getServletContext();
        String indexFilePath = context.getRealPath("/templates") + "\\";

        resolverService = new ResolverService(indexFilePath);
        engine = resolverService.getEngine();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String timeParameter = req.getParameter("timezone");
        String dateTime = DateTimeService.get(timeParameter);

        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("currentTime", dateTime)
        );

        resp.setHeader("Set-Cookie", "lastTimezone=" + DateTimeService.formatTimeZone(timeParameter));
        engine.process("index", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}