package org.example.learningsystem.core.template.service;

/**
 * Interface for rendering text templates with dynamic content.
 * <p>
 * Implementations process template files, replace placeholders with values from
 * the provided arguments object according to the template engine's syntax rules.
 */
public interface RenderTemplateService {

    /**
     * Renders a template file with the provided arguments.
     *
     * @param path      the classpath-relative path to the template file (e.g, "templates/example.html")
     * @param arguments the object containing values for template placeholders
     * @return the rendered template content
     */
    String render(String path, Object arguments);
}
