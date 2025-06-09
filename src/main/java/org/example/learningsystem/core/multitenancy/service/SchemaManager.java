package org.example.learningsystem.core.multitenancy.service;

public interface SchemaManager {

    void create(String subdomain);

    void delete(String subdomain);
}
