package global.goit.edu.servlets;

import global.goit.edu.datetimeservice.DateTimeService;
import global.goit.edu.resolver.ResolverService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(value = "")
public class IndexServlet extends HttpServlet {
    private ResolverService resolverService;
    private TemplateEngine engine;

    @Override
    public void init() throws ServletException {
        // Get the real path of the index.html file
        ServletContext context = getServletContext();
        String indexFilePath = context.getRealPath("/templates") + "\\";
        System.out.println(indexFilePath);
        resolverService = new ResolverService(indexFilePath);
        engine = resolverService.getEngine();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String timezone = "";
        String currentTime = "";

        timezone = CookiesChecker.getTimeZone(req);

        if (timezone.isEmpty()) {
            currentTime = DateTimeService.get();
        } else {
            currentTime = DateTimeService.get(timezone);
        }

        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("currentTime", currentTime)
        );

        engine.process("index", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}