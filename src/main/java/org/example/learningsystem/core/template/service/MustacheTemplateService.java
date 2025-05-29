package org.example.learningsystem.core.template.service;

import com.samskivert.mustache.Mustache;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.core.template.exception.RenderTemplateException;
import org.springframework.boot.autoconfigure.mustache.MustacheResourceTemplateLoader;
import org.springframework.stereotype.Service;

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

    private String tryToRender(String path, Object arguments) throws Exception {
        var reader = templateLoader.getTemplate(path);
        return Mustache.compiler()
                .compile(reader)
                .execute(arguments);
    }

}
