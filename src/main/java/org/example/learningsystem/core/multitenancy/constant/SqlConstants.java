package org.example.learningsystem.core.multitenancy.constant;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class SqlConstants {

    public static final String CREATE_SCHEMA_SQL = "CREATE SCHEMA %s";
    public static final String DROP_SCHEMA_SQL = "DROP SCHEMA %s CASCADE";
    public static final String SELECT_SCHEMAS_SQL = "SELECT schema_name FROM information_schema.schemata";
}
