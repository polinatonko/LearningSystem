package org.example.learningsystem.core.template.service;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.MustacheException;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.core.template.exception.RenderTemplateException;
import org.springframework.boot.autoconfigure.mustache.MustacheResourceTemplateLoader;
import org.springframework.stereotype.Service;

/**
 * {@link RenderTemplateService} implementation using Mustache template engine.
 */
@Service
@RequiredArgsConstructor
public class MustacheTemplateService implements RenderTemplateService {

    private final MustacheResourceTemplateLoader templateLoader;

    @Override
    public String render(String path, Object arguments) {
        try {
            return tryToRender(path, arguments);
        } catch (Exception e) {
            throw new RenderTemplateException("Error during rendering %s: %s".formatted(path, e.getMessage()));
        }
    }

    /**
     * Attempts to load and render the specified template.
     *
     * @param path      the template path
     * @param arguments the object containing template placeholders
     * @return the rendered template content
     * @throws MustacheException if template execution fails
     * @throws Exception         if template file cannot be read
     */
    private String tryToRender(String path, Object arguments) throws Exception {
        var reader = templateLoader.getTemplate(path);
        return Mustache.compiler()
                .compile(reader)
                .execute(arguments);
    }

}
