package org.example.learningsystem.core.template.service;

import com.samskivert.mustache.Mustache;
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
            var reader = templateLoader.getTemplate(path);
            return Mustache.compiler()
                    .compile(reader)
                    .execute(arguments);
        } catch (Exception e) {
            throw new RenderTemplateException("Error during rendering %s: %s".formatted(path, e.getMessage()));
        }
    }
}
