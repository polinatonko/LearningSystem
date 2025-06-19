package org.example.learningsystem.multitenancy.db.schema;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.example.learningsystem.multitenancy.constant.SqlConstants.CREATE_SCHEMA_SQL;
import static org.example.learningsystem.multitenancy.constant.SqlConstants.DROP_SCHEMA_SQL;
import static org.example.learningsystem.multitenancy.constant.SqlConstants.SELECT_SCHEMAS_SQL;

@Component
@Profile("!cloud")
@RequiredArgsConstructor
public class LocalSchemaHelper {

    private static final String SCHEMA_NAME_PATTERN = "\\w+";

    private final JdbcClient jdbcClient;

    public void create(String schema) {
        validateSchemaName(schema);
        jdbcClient.sql(CREATE_SCHEMA_SQL.formatted(schema))
                .update();
    }

    public void drop(String schema) {
        validateSchemaName(schema);
        jdbcClient.sql(DROP_SCHEMA_SQL.formatted(schema))
                .update();
    }

    public List<String> getAll() {
        return jdbcClient.sql(SELECT_SCHEMAS_SQL)
                .query(String.class)
                .list();
    }

    private static void validateSchemaName(String schema) {
        if (!schema.matches(SCHEMA_NAME_PATTERN)) {
            throw new IllegalArgumentException("Invalid schema name provided [schema = %s]".formatted(schema));
        }
    }
}
