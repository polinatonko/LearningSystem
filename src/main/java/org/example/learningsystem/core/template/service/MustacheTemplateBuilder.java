package org.example.learningsystem.core.template.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.core.template.exception.RenderTemplateException;
import org.springframework.boot.autoconfigure.mustache.MustacheResourceTemplateLoader;
import org.springframework.stereotype.Service;

import static com.samskivert.mustache.Mustache.Compiler;

/**
 * {@link TemplateBuilder} implementation using Mustache template engine.
 */
@Service
@RequiredArgsConstructor
public class MustacheTemplateBuilder implements TemplateBuilder {

    private final Compiler mustacheCompiler;
    private final MustacheResourceTemplateLoader templateLoader;

    @Override
    public String build(String path, Object arguments) {
        try {
            var reader = templateLoader.getTemplate(path);
            var template = mustacheCompiler.compile(reader);
            return template.execute(arguments);
        } catch (Exception e) {
            throw new RenderTemplateException("Error occurred while reading template [path = %s]".formatted(path), e);
        }
    }
}
