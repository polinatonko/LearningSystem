package org.example.learningsystem.core.template.service;

import com.samskivert.mustache.Mustache;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.core.template.exception.RenderTemplateException;
import org.springframework.boot.autoconfigure.mustache.MustacheResourceTemplateLoader;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MustacheTemplateService implements RenderTemplateService {

    private static final String TEMPLATES_PREFIX = "templates/";
    private static final String TEMPLATES_SUFFIX = ".mustache";

    private final MustacheResourceTemplateLoader templateLoader =
            new MustacheResourceTemplateLoader(TEMPLATES_PREFIX, TEMPLATES_SUFFIX);

    @Override
    public String render(String path, Object arguments) {
        try {
            return tryRender(path, arguments);
        }
        catch (Exception e) {
            log.error("Error during rendering {}: {}", path, e.getMessage());
            throw new RenderTemplateException(e.getMessage());
        }
    }

    private String tryRender(String path, Object arguments) throws Exception {
        var reader = templateLoader.getTemplate(path);
        return Mustache.compiler()
                .compile(reader)
                .execute(arguments);
    }

}
