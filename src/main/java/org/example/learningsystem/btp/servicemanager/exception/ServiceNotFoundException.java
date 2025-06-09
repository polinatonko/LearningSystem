package org.example.learningsystem.btp.servicemanager.exception;

public class ServiceNotFoundException extends RuntimeException {

    public ServiceNotFoundException(String serviceName) {
        super("Service %s was not found".formatted(serviceName));
    }
}
