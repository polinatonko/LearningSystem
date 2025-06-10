package org.example.learningsystem.btp.servicemanager.common.exception;

public class ServiceNotFoundException extends RuntimeException {

    public ServiceNotFoundException(String property, String propertyValue) {
        super("Service was not found [%s = %s]".formatted(property, propertyValue));
    }
}
