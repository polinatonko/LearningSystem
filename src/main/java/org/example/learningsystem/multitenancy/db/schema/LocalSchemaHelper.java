package org.example.learningsystem.multitenancy.db.schema;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.example.learningsystem.core.db.constant.SqlConstants.CREATE_SCHEMA_SQL;
import static org.example.learningsystem.core.db.constant.SqlConstants.DROP_SCHEMA_SQL;
import static org.example.learningsystem.core.db.constant.SqlConstants.SELECT_SCHEMAS_SQL;

/**
 * A helper class for managing database schemas in a non-cloud environment via direct executing of
 * SQL statements.
 */
@Component
@Profile("!cloud")
@RequiredArgsConstructor
public class LocalSchemaHelper {

    private static final String SCHEMA_NAME_PATTERN = "\\w+";

    private final JdbcClient jdbcClient;

    /**
     * Creates a new database schema.
     *
     * @param schema the name of the schema to create (must match {@value #SCHEMA_NAME_PATTERN})
     */
    public void create(String schema) {
        validateSchemaName(schema);
        jdbcClient.sql(CREATE_SCHEMA_SQL.formatted(schema))
                .update();
    }

    /**
     * Drops database schema.
     *
     * @param schema the name of the schema to drop (must match {@value #SCHEMA_NAME_PATTERN})
     */
    public void drop(String schema) {
        validateSchemaName(schema);
        jdbcClient.sql(DROP_SCHEMA_SQL.formatted(schema))
                .update();
    }

    /**
     * Retrieves all schema names in the current database.
     *
     * @return a list of schema names
     */
    public List<String> getAll() {
        return jdbcClient.sql(SELECT_SCHEMAS_SQL)
                .query(String.class)
                .list();
    }

    private static void validateSchemaName(String schema) {
        if (!schema.matches(SCHEMA_NAME_PATTERN)) {
            throw new IllegalArgumentException("Invalid schema name provided [name = %s]".formatted(schema));
        }
    }
}
