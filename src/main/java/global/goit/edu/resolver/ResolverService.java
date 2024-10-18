package global.goit.edu.resolver;

import lombok.Data;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;

@Data
public class ResolverService {
    private static String indexPagePath;
    private FileTemplateResolver resolver;
    private TemplateEngine engine;

    public ResolverService(String pagePath) {
        indexPagePath = pagePath;
        engine = new TemplateEngine();
        resolver = new FileTemplateResolver();
        resolver.setPrefix(indexPagePath);
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
    }
}