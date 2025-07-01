package org.example.learningsystem.core.template.service;

/**
 * Interface for building text templates with dynamic content.
 * <p>
 * Implementations process template files, replace placeholders with values from
 * the provided arguments object according to the template engine's syntax rules.
 */
public interface TemplateBuilder {

    /**
     * Builds a template with the provided arguments.
     *
     * @param path      the path to the template file (e.g, "templates/example.html")
     * @param args the object containing values for template placeholders
     * @return the rendered template content
     */
    String build(String path, Object args);
}
