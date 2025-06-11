package org.example.learningsystem.btp.servicemanager.common.constant;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ServiceManagerResourceConstants {

    public static final String DATABASE_ID = "database_id";
    public static final String NAME = "name";
    public static final String TENANT_ID = "tenant_id";
    public static final String BASE_PATH = "v1";
    public static final String EQUALS_QUERY = "%s eq '%s'";
    public static final String FIELD_QUERY = "fieldQuery";
    public static final String LABEL_QUERY = "labelQuery";
    public static final String SERVICE_BINDINGS = "service_bindings";
    public static final String SERVICE_INSTANCES = "service_instances";
}
