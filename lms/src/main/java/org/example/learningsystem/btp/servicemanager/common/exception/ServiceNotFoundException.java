package org.example.learningsystem.btp.servicemanager.common.exception;

/**
 * Exception thrown when a requested through Service Manager API service cannot be found.
 */
public class ServiceNotFoundException extends RuntimeException {

    /**
     * Constructor for the {@link ServiceNotFoundException}.
     *
     * @param property      the property name that was used for searching
     * @param propertyValue the value that was searched for
     */
    public ServiceNotFoundException(String property, String propertyValue) {
        super("Service was not found [%s = %s]".formatted(property, propertyValue));
    }
}
